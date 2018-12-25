package codesquad.domain;

import java.util.ArrayList;
import java.util.List;

public class Paging {

    public static final int COUNT_OF_PAGING_CONTENTS = 3;
    public static final int COUNT_OF_PAGING = 3;

    private int pageNo;
    private int prev;
    private int next;
    private List<Integer> pages;

    public Paging(Long pageNo) {
        this.pageNo = 1;
        if(pageNo != null) {
            this.pageNo = pageNo.intValue();
        }

        this.pages = new ArrayList<>();
        this.prev = 0;
        this.next = 0;
    }

    private Paging(int prev, int next, List<Integer> pages) {
        this.prev = prev;
        this.next = next;
        this.pages = pages;
    }

    public Paging of(long countOfContents) {
        int total = (int)Math.ceil(countOfContents / COUNT_OF_PAGING_CONTENTS);
        int start = (this.pageNo / (COUNT_OF_PAGING + 1)) * COUNT_OF_PAGING + 1;
        int end = total < start + COUNT_OF_PAGING - 1 ? total : start + COUNT_OF_PAGING - 1;

        for (int i = start; i <= end; i++) {
            this.pages.add(i);
        }

        if(start > 1) {
            this.prev = start - 1;
        }

        if(end != total) {
            this.next = end + 1;
        }

        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPrev() {
        return prev;
    }

    public int getNext() {
        return next;
    }

    public List<Integer> getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "pageNo=" + pageNo +
                ", prev=" + prev +
                ", next=" + next +
                ", pages=" + pages +
                '}';
    }
}
