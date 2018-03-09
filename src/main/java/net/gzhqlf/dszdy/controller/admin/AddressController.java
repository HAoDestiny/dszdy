package net.gzhqlf.dszdy.controller.admin;

import net.gzhqlf.dszdy.exception.ControllerException;
import net.gzhqlf.dszdy.po.ResultPo;
import net.gzhqlf.dszdy.service.admin.AddressService;
import net.gzhqlf.dszdy.vo.AddressMsgVo;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by DESTINY on 2017/10/24.
 */

@RestController
@RequestMapping(value = "/api/address", method = RequestMethod.POST)
public class AddressController {

    @Resource
    private AddressService addressService;

    @RequestMapping(value = {"/getAddressListBath"})
    public ResultPo getAddressListBath(@RequestBody @Valid AddressMsgVo addressMsgVo,
                                       BindingResult bindingResult) throws ControllerException {

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new ControllerException(list.get(0).getDefaultMessage() + "不能为空！");
        }

        return addressService.getAddressListBath(addressMsgVo);
    }

}
