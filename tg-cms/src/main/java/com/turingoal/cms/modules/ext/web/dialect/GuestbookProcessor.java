package com.turingoal.cms.modules.ext.web.dialect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.util.StringUtils;
import com.turingoal.cms.modules.ext.service.FriendlinkService;

/**
 * 友情链接Processor
 */
@Component
public class GuestbookProcessor extends AbstractAttributeTagProcessor {
    public static final int PRECEDENCE = 199; // 优先级 Thymeleaf标准each优先级是200
    public static final String DEFAULT_DIALECT_PREFIX = "tg_cms"; // 默认前缀
    public static final String ATTR_NAME = "frendlink"; // 属性名称
    public static final String STATUS_VAR_NAME = "itemStat";
    @Autowired
    private FriendlinkService friendlinkService;

    public GuestbookProcessor() {
        super(TemplateMode.HTML, DEFAULT_DIALECT_PREFIX, null, false, ATTR_NAME, true, PRECEDENCE, true);
    }

    /**
     * 处理标签
     */
    @Override
    protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag, final AttributeName attributeName, final String attributeValue, final IElementTagStructureHandler structureHandler) {
        String iterVarName = attributeValue.trim(); // 获取参数
        if (StringUtils.isEmptyOrWhitespace(iterVarName)) {
            throw new TemplateProcessingException("参数不能为空！");
        }
        Object iteratedValue = friendlinkService.findByType(null); // 数据集
        structureHandler.iterateElement(iterVarName, STATUS_VAR_NAME, iteratedValue);
    }
}
