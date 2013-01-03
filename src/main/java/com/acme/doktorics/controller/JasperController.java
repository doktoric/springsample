package com.acme.doktorics.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.acme.doktorics.domain.Message;
import com.acme.doktorics.service.IMessageService;

@Controller
@RequestMapping("/report/")
public class JasperController {

    @Autowired
    private IMessageService messageService;
    private static final Logger logger = LoggerFactory.getLogger(JasperController.class);

    @RequestMapping(method = RequestMethod.GET, value = "pdf")
    @ResponseBody
    public ModelAndView generatePdfReport()  {

        logger.debug("--------------generate Jasper PDF report----------");
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<Message> usersList = messageService.getAll();
        JRDataSource JRdataSource = new JRBeanCollectionDataSource(usersList);
        parameterMap.put("datasource", JRdataSource);
        // pdfReport bean has ben declared in the jasper-views.xml file
        ModelAndView resultModelAndView = new ModelAndView("pdfReport", parameterMap);
        return resultModelAndView;
    }// generatePdfReport
    
    
  
    
   
    
}
