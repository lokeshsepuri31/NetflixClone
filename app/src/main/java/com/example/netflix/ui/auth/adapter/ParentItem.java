package com.example.netflix.ui.auth.adapter;

import java.util.List;

public class ParentItem {

    private final String parentItemTitle;
    private final List<ChildItem> childItemList;

    public ParentItem(
            String parentItemTitle,
            List<ChildItem> childItemList)
    {

        this.parentItemTitle = parentItemTitle;
        this.childItemList = childItemList;
    }

    public String getParentItemTitle()
    {
        return parentItemTitle;
    }

    public List<ChildItem> getChildItemList()
    {
        return this.childItemList;
    }
}

