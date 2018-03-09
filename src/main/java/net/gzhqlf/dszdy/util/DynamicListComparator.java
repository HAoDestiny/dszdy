package net.gzhqlf.dszdy.util;

import net.gzhqlf.dszdy.po.DynamicPo;

import java.util.Comparator;

/**
 * Created by Destiny_hao on 2017/10/28.
 */
public class DynamicListComparator implements Comparator {

    @Override
    public int compare(Object arg0, Object arg1) {

        DynamicPo dynamicPoA = (DynamicPo) arg0;
        DynamicPo dynamicPoB = (DynamicPo) arg1;
        return dynamicPoB.getTime().compareTo(dynamicPoA.getTime());
    }
}

