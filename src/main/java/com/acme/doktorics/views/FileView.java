package com.acme.doktorics.views;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;


public class FileView extends AbstractView {
    private static Logger logger = LoggerFactory.getLogger(FileView.class);
    private String fileName;
    private byte[] content;

    @Override
    protected void renderMergedOutputModel(Map<String, Object> arg0, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(getContentType());
        String encodedFileName = encodeUrlPathSegment(this.fileName, request);
        response.setHeader("Content-disposition", "attachment; filename=\"" + encodedFileName + "\"");

        if (content != null) {
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
        }
    }

    private String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String characterEncoding = httpServletRequest.getCharacterEncoding();
        if (characterEncoding == null) {
            characterEncoding = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        String encodedPathSegment = null;

        try {
            encodedPathSegment = UriUtils.encodePathSegment(pathSegment, characterEncoding);
        } catch (UnsupportedEncodingException uee) {
            logger.error("Could not encode pathSegment", uee);
        }

        return encodedPathSegment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
