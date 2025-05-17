package com.kenyajug.regression.web.controllers;
/*
 * MIT License
 *
 * Copyright (c) 2025 Kenya JUG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import com.kenyajug.regression.application.ApplicationsRepository;
import com.kenyajug.regression.applogger.repository.LogsDataSourceRepository;
import com.kenyajug.regression.user.UserRepository;
import com.kenyajug.regression.security.SecurityHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class DashboardController {
    private final UserRepository userRepository;
    private final SecurityHelper securityHelper;
    private final ApplicationsRepository applicationsRepository;
    private final LogsDataSourceRepository logsDataSourceRepository;
    public DashboardController(UserRepository userRepository, SecurityHelper securityHelper, ApplicationsRepository applicationsRepository, LogsDataSourceRepository logsDataSourceRepository) {
        this.userRepository = userRepository;
        this.securityHelper = securityHelper;
        this.applicationsRepository = applicationsRepository;
        this.logsDataSourceRepository = logsDataSourceRepository;
    }
    @GetMapping("/")
    public String dashboard(Model model){
        var principal = securityHelper.findAuthenticatedUser();
        var user = userRepository.findByUsername(principal.getUsername()).orElseThrow(() -> new SecurityException("Invalid session, current user is not authenticated"));
        var apps = applicationsRepository.findByOwner(user);
        var datasourceList = logsDataSourceRepository.findAll();
        model.addAttribute("apps",apps);
        model.addAttribute("logsDatasourceList",datasourceList);
        return "dashboard";
    }
}
