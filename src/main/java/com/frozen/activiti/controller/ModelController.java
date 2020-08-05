package com.frozen.activiti.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.frozen.activiti.common.PageConstants;
import com.frozen.activiti.exception.BaseException;
import com.frozen.activiti.util.ResponseUtil;
import com.frozen.activiti.util.WebUtil;
import com.frozen.activiti.vo.PaginationData;
import com.frozen.activiti.vo.ResponseDataResult;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.EditorJsonConstants;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 模型管理
 */
@RestController
@RequestMapping("models")
public class ModelController {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RuntimeService runtimeService;

    /**
     * 新建一个空模型
     *
     * @return
     */
    @PostMapping("newModel")
    public Object newModel() {
        //初始化一个空模型
        Model model = repositoryService.newModel();

        //设置一些默认信息
        String processName = "流程名称";
        String processKey = "流程Key";
        String description = "流程描述";
        int revision = 1;
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
        model.setName(processName);
        model.setKey(processKey);
        model.setMetaInfo(modelNode.toString());
        model.setVersion(revision);
        repositoryService.saveModel(model);
        String modelId = model.getId();

        //完善ModelEditorSource 否则页面流程编辑器无资源
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put(EditorJsonConstants.EDITOR_STENCIL_ID, "canvas");
        editorNode.put(EditorJsonConstants.EDITOR_SHAPE_ID, "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.set("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(modelId, editorNode.toString().getBytes(StandardCharsets.UTF_8));
        return ResponseUtil.getResponseSuccess(modelId);
    }


    /**
     * 发布模型为流程定义
     *
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/deploy/{id}")
    public ResponseDataResult<Object> deploy(@PathVariable("id") String id) throws Exception {

        //获取模型
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
        byte[] extraBytes = repositoryService.getModelEditorSourceExtra(modelData.getId());
        if (bytes == null) {
            throw new BaseException("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(extraBytes);
        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            throw new BaseException("数据模型不符要求，请至少设计一条主线流程。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        String processXmlString = new String(bpmnBytes, StandardCharsets.UTF_8);
        String processPngName = modelData.getName() + ".png";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, processXmlString)
                .addInputStream(processPngName, byteArrayInputStream)
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        return ResponseUtil.getResponseSuccess();
    }

    @GetMapping
    public ResponseDataResult<PaginationData<Model>> getList() {
        return getList(1, PageConstants.DEFAULT_PAGE_SIZE);
    }

    @GetMapping("/{page}/{pageSize}")
    public ResponseDataResult<PaginationData<Model>> getList(@PathVariable Integer page, @PathVariable Integer pageSize) {
        List<Model> list = repositoryService.createModelQuery().listPage(pageSize * (page - 1)
                , pageSize);
        long totalCount = repositoryService.createModelQuery().count();

        PaginationData<Model> paginationData = WebUtil.getPaginationData(page, list, pageSize, (int) totalCount);
        return ResponseUtil.getResponseSuccess(paginationData);
    }

    @DeleteMapping("{id}")
    @Transactional(rollbackFor = Exception.class)
    public Object deleteOne(@PathVariable("id") String id) {
        repositoryService.deleteModel(id);
        return ResponseUtil.getResponseSuccess();
    }

    @PostMapping("/startProcess/{processDefinitionKey}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseDataResult<Object> startProcess(@PathVariable String processDefinitionKey, @RequestBody(required = false) Map<String, Object> params) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey, params);
        return ResponseUtil.getResponseSuccess();
    }
}
