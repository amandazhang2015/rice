/**
 * Copyright 2005-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krad.demo.uif.controller;

import org.kuali.rice.krad.demo.uif.form.KradSampleAppForm;
import org.kuali.rice.krad.file.FileMeta;
import org.kuali.rice.krad.file.FileMetaBlob;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.view.ViewTheme;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * Basic controller for the KRAD sample application
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@Controller
@RequestMapping(value = "/kradsampleapp")
public class KradSampleAppController extends UifControllerBase {

    @Override
    protected KradSampleAppForm createInitialForm() {
        return new KradSampleAppForm();
    }

    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=changeTheme")
    public ModelAndView changeTheme(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        changeTheme(form);
        return getModelAndView(form);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=validateView")
    public ModelAndView validateView(@ModelAttribute("KualiForm") UifFormBase uiTestForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        KRADServiceLocatorWeb.getViewValidationService().validateView(uiTestForm);
        return getModelAndView(uiTestForm);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=addGrowl")
    public ModelAndView addGrowl(@ModelAttribute("KualiForm") UifFormBase uiTestForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        String extraInfo = (String) request.getParameter("extraInfo");
        if (extraInfo == null) {
            extraInfo = "none";
        }
        GlobalVariables.getMessageMap().addGrowlMessage("Growl Message", "demo.fakeGrowl", extraInfo);
        return getModelAndView(uiTestForm);
    }

    private void changeTheme(UifFormBase form) {
        String theme = ((KradSampleAppForm) form).getThemeName();
        if (theme != null) {
            ViewTheme newTheme = (ViewTheme) (KRADServiceLocatorWeb.getDataDictionaryService().getDictionaryBean(
                    theme));
            if (newTheme != null) {
                form.getView().setTheme(newTheme);
            }
        }
    }

    /**
     * Changes the view to readOnly and returns.
     *
     * @param uifForm
     * @param result
     * @param request
     * @param response
     * @return readOnly View
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=makeReadOnly")
    public ModelAndView makeReadOnly(@ModelAttribute("KualiForm") UifFormBase uifForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        //set View to readOnly
        uifForm.getView().setReadOnly(true);
        return getModelAndView(uifForm);
    }

    /**
     * Adds errors to fields defined in the validationMessageFields array
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=addStandardSectionsErrors")
    public ModelAndView addStandardSectionsErrors(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        GlobalVariables.getMessageMap().putError("Demo-ValidationMessages-Section1", "errorSectionTest");
        GlobalVariables.getMessageMap().putError("Demo-ValidationMessages-Section2", "errorSectionTest");

        Set<String> inputFieldIds = form.getViewPostMetadata().getInputFieldIds();
        for (String id : inputFieldIds) {
            if (form.getViewPostMetadata().getComponentPostData(id, UifConstants.PostMetadata.PATH) != null) {
                String key = (String) form.getViewPostMetadata().getComponentPostData(id, UifConstants.PostMetadata.PATH);
                GlobalVariables.getMessageMap().putError(key, "error1Test");
            }
        }

        return getModelAndView(form);
    }

    /**
     * Refreshes the group
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=refreshProgGroup")
    public ModelAndView refreshProgGroup(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        return getModelAndView(form);
    }

    /**
     * Refresh and set server messages
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=refreshWithServerMessages")
    public ModelAndView refreshWithServerMessages(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        GlobalVariables.getMessageMap().putError("inputField4", "serverTestError");
        GlobalVariables.getMessageMap().putWarning("inputField4", "serverTestWarning");
        GlobalVariables.getMessageMap().putInfo("inputField4", "serverTestInfo");

        return getModelAndView(form);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=customRefresh")
    public ModelAndView customRefresh(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {
        GlobalVariables.getMessageMap().addGrowlMessage("Test", "serverTestInfo");

        return getModelAndView(form);
    }

    @Override
    public void sendFileFromLineResponse(UifFormBase uifForm, HttpServletRequest request, HttpServletResponse response,
            List<FileMeta> collection, FileMeta fileLine) throws Exception{
        if (fileLine instanceof FileMetaBlob) {
            InputStream is = ((FileMetaBlob) fileLine).getBlob().getBinaryStream();
            response.setContentType(fileLine.getContentType());
            response.setHeader("Content-Disposition", "attachment; filename=" + fileLine.getName());

            // copy it to response's OutputStream
            FileCopyUtils.copy(is, response.getOutputStream());

            response.flushBuffer();
        }
    }
}
