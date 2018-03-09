package net.gzhqlf.dszdy.util;

import net.gzhqlf.dszdy.entity.ChatRecordEntity;

import java.util.Comparator;

public class ChatRecordListComparator implements Comparator {

    @Override
    public int compare(Object arg0, Object arg1){

        ChatRecordEntity chatRecordEntityA = (ChatRecordEntity)arg0;
        ChatRecordEntity chatRecordEntityB = (ChatRecordEntity)arg1;

        return chatRecordEntityA.getCreateTime().compareTo(chatRecordEntityB.getCreateTime());
    }
}