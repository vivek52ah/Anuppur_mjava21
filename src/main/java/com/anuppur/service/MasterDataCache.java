package com.anuppur.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MasterDataCache {
    public Map<String, Long> financialYearMap = new HashMap<>();
    public Map<String, Long> schemeMap = new HashMap<>();
    public Map<String, Long> workTypeMap = new HashMap<>();
    public Map<String, Long> workCategoryMap = new HashMap<>();
    public Map<String, Long> workSubTypeMap = new HashMap<>();
    public Map<String, Long> workStatusMap = new HashMap<>();
    public Map<String, Long> implAgencyMap = new HashMap<>();
    public Map<String, Long> districtMap = new HashMap<>();
    public Map<String, String> districtCodeMap = new HashMap<>();
    public Map<String, Long> blockMap = new HashMap<>();
    public Map<String, String> blockCodeMap = new HashMap<>();
    public Map<String, Long> gramPanchayatMap = new HashMap<>();
    public Map<String, String> gramPanchayatCodeMap = new HashMap<>();
    public Map<String, Long> workHeadMap = new HashMap<>();
    public Map<String, Long> workPriorityMap = new HashMap<>();
    public Map<String, Long> financialHeadMap = new HashMap<>();
    public Map<String, Long> vidhanSabhaMap = new HashMap<>();
    public Set<String> existingWorkNos = new HashSet<>();

    // Fixed division/district constants (mirrors addWorkDetail hardcoded values)
    public Long divisionId = 3L;
    public String districtCode = "461";
}
