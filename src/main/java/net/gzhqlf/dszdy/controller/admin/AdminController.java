package net.gzhqlf.dszdy.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Destiny_hao on 2017/11/5.
 */

@Controller
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdminController {

    @RequestMapping(value = "/")
    public String login() {

        return "admin/login";
    }

    @RequestMapping(value = "/{forward}")
    public String reward(@PathVariable String forward) {

        return "admin/" + forward;
    }

}
