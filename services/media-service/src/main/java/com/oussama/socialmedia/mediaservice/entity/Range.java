package com.oussama.socialmedia.mediaservice.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Range {
    private final long start;
    private final long end;

    public long getRangeEnd(long fileSize){
        return Math.min(end,fileSize-1);
    }

    public static Range parseHttpRangeString(String httpRangeString,int defaultChunkSize){
        if(httpRangeString==null)return Range.builder().start(0).end(defaultChunkSize).build();
        int dashIndex = httpRangeString.indexOf('-');
        long startRange = Long.parseLong(httpRangeString.substring(6, dashIndex));
        String endRangeString = httpRangeString.substring(dashIndex+1);
        if(endRangeString.isEmpty())return Range.builder().start(startRange).end(startRange+defaultChunkSize).build();
        long endRange = Long.parseLong(endRangeString);
        return Range.builder().start(startRange).end(endRange).build();
    }
}
