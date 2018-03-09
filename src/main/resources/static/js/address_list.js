function address_list(province_obj, city_obj, area_obj, init_opt) {
    var _this_obj = this;

    var call_back_func = init_opt == null ? null : init_opt.callback;
    var init_selection = init_opt == null ? null : init_opt.init_selection;
    var auto_disable = init_opt == null ? false : init_opt.auto_disable;

    this.SelectedProvinceID = null;      //选中的省ID
    this.SelectedProvinceName = null;  //选中的省名

    this.SelectedCityID = null; 		 //选中的城市ID
    this.SelectedCityName = null; 	 //选中的城市名

    this.SelectedAreaID = null; 		 //选中的地区ID
    this.SelectedAreaName = null;        //选中的地区名

    var dt_area_info = null;

    if (init_selection != null && init_selection.length == 3) //有初始化的参数
        _LoadAddressList(init_selection);
    else
        loadAddressList(province_obj, "PROVINCE");

    this.LoadAddressList = _LoadAddressList;

    function _LoadAddressList(init_selection_list) {
        var init_province_id = init_selection_list[0];
        var init_city_id = init_selection_list[1];
        var init_area_id = init_selection_list[2];

        var param = {"province_id":init_province_id, "type": "0", "city_id": init_city_id, "area_id": init_area_id, "father_id":"0"};

        dszdy.Business.Address.getAddressListBath(param, function (ret) {
            if (ret.status != "SUCCESS")
                return;
            var dt_province_list = ret.data.list;
            var dt_city_list = ret.data.city;
            var dt_area_list = ret.data.area;

            if (dt_province_list != null) {
                fillSelector(province_obj, dt_province_list, init_province_id);

                if (dt_city_list != null) {
                    fillSelector(city_obj, dt_city_list, init_city_id);

                    if (dt_area_list != null) {
                        fillSelector(area_obj, dt_area_list, init_area_id);
                        dt_area_info = dt_area_list;
                    }
                }
            }
            addressOnChange();
        })
    }

    function addressOnChange() {
        if (province_obj.val() == "noSelect") {
            if (auto_disable) {
                if (typeof $().prop == "undefined") {
                    clear_selection(city_obj).attr('disabled', true);
                    clear_selection(area_obj).attr('disabled', true);
                }
                else {
                    clear_selection(city_obj).prop('disabled', true);
                    clear_selection(area_obj).prop('disabled', true);
                }
            }
            else {
                clear_selection(city_obj).hide();
                clear_selection(area_obj).hide();
            }
        }
        else if (city_obj.val() == "noSelect") {
            if (auto_disable) {
                if (typeof $().prop == "undefined")
                    clear_selection(area_obj).attr('disabled', true);
                else
                    clear_selection(area_obj).prop('disabled', true);
            }
            else
                clear_selection(area_obj).hide();

        }
        _this_obj.SelectedProvinceID = province_obj.val() == "noSelect" ? null : province_obj.val();
        _this_obj.SelectedProvinceName = province_obj.val() == "noSelect" ? null : province_obj.find("option:selected").text();

        _this_obj.SelectedCityID = city_obj.val() == "noSelect" ? null : city_obj.val();
        _this_obj.SelectedCityName = city_obj.val() == "noSelect" ? null : city_obj.find("option:selected").text();

        _this_obj.SelectedAreaID = area_obj.val() == "noSelect" ? null : area_obj.val();
        _this_obj.SelectedAreaName = area_obj.val() == "noSelect" ? null : area_obj.find("option:selected").text();

        _this_obj.SelectedAreaCODClass = null;
        _this_obj.SelectedAreaCODAllowArea = null;
        _this_obj.SelectedAreaCODDenyArea = null;

        if (call_back_func != null)
            call_back_func(_this_obj);
    }

    province_obj.unbind('change').bind('change', function () {
        if ($(this).val() == "noSelect")
            return addressOnChange();

        loadAddressList(city_obj, "1", $(this).val());
    });

    city_obj.unbind('change').bind('change', function () {
        if ($(this).val() == "noSelect")
            return addressOnChange();

        loadAddressList(area_obj, "2", $(this).val());
    });

    area_obj.unbind('change').bind('change', function () {
        addressOnChange();
    });

    function loadAddressList(select_obj, child_address_type, father_id) {
        var param;
        if (child_address_type == "PROVINCE")
            param = {"province_id": "0", "type": "0", "city_id": "0", "area_id": "0", "father_id":"0"};
        else
            param = {"province_id": "0", "type": child_address_type, "city_id": "0", "area_id": "0", "father_id": father_id};
        dszdy.Business.Address.getAddressListBath(param, function (ret) {
            if (ret.status != "SUCCESS")
                return;
            var menu_dt = ret.data.list;
            fillSelector(select_obj, menu_dt);
            if (child_address_type == "2")
                dt_area_info = menu_dt;
            else
                dt_area_info = null;
            select_obj.show();
            addressOnChange();
        });
    }

    function fillSelector(select_obj, menu_dt, init_id) {
        if (init_id == undefined) {
            init_id = "noSelect";
        }
        clear_selection(select_obj);

        for (var i = 0; i < menu_dt.length; i++) {
            var option_value = menu_dt[i]["code"];
            var province_id = menu_dt[i]["father"];
            var option_text;
            option_text = menu_dt[i]["name"];
            var new_option = new Option(option_text, option_value);
            select_obj[0].options.add(new_option);

            if (init_id != null && init_id == option_value)
                select_obj[0].options[select_obj[0].options.length - 1].selected = true;
        }
        if (select_obj.attr("id") == "Province") {
            updateSelectAddress("Province", "Province", init_id);
        }
        else if (select_obj.attr("id") == "City") {
            updateSelectAddress("City", "City", init_id);
        }
        else if (select_obj.attr("id") == "Area") {
            updateSelectAddress("Area", "Area", init_id);
        }
        else if (select_obj.attr("id") == "ProvinceWork") {
            updateSelectAddress("ProvinceWork", "ProvinceWork", init_id);
        }
        else if (select_obj.attr("id") == "CityWork") {
            updateSelectAddress("CityWork", "CityWork", init_id);
        }
        else if (select_obj.attr("id") == "AreaWork") {
            updateSelectAddress("AreaWork", "AreaWork", init_id);
        }
        if (typeof $().prop == "undefined")
            return select_obj.show().attr("disabled", false);
        else
            return select_obj.show().prop("disabled", false);
    }

    function updateSelectAddress(className, id, value) {
        var obj = $('#' + className).mobiscroll().select({
            theme: 'mobiscroll',
            display: 'bottom',
            mode: 'scroller',
            lang: 'zh',
            animate: 'fade',
            defaultValue: value,
        });
    }

    function clear_selection(select_obj) {
        select_obj[0].options.length = 0;
        select_obj[0].options.add(new Option("请选择", "0"));
        return select_obj;
    }
}

//初始化地址选择器
function getAddressOnProfile(ProvinceWork, CityWork, AreaWork) {
    ////绑定地址下拉框
    var prov_select_work = $("select[name='ProvinceWork']");
    var city_select_work = $("select[name='CityWork']");
    var area_select_work = $("select[name='AreaWork']");
    _edit_addr_selection_work = new address_list(prov_select_work, city_select_work, area_select_work,
        {
            init_selection: [ProvinceWork, CityWork, AreaWork]
        });
}

function getAddress(Province, City, Area, ProvinceWork, CityWork, AreaWork) {

    ////绑定地址下拉框
    var prov_select = $("select[name='Province']");
    var city_select = $("select[name='City']");
    var area_select = $("select[name='Area']");
    _edit_addr_selection = new address_list(prov_select, city_select, area_select,
        {
            init_selection: [Province, City, Area]
        });
}