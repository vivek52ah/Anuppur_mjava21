# Sanction Details - HTML Structure Reference

## File Location
`src/main/resources/templates/common/work/editTender.html`

## Main Container
```html
<div th:fragment="edit_Tender">
    <form name="workTenderForm" id="workFormId">
        <!-- All tender fields are inside this form -->
    </form>
</div>
```

## Work Status Selection (Lines 200-240)
```html
<div class="work-status-tiles">
    <ul class="nav nav-pills work-status-nav">
        <li class="nav-item" ng-repeat="workStatus in worksStatus"
            ng-if="workData.isTenders=='1' || workStatus.workStatusId=='8'">
            <a href="javascript:void(0)" class="nav-link"
               ng-class="{'active': workDataTender.workStatusId == workStatus.workStatusId}"
               ng-click="workDataTender.workStatusId = workStatus.workStatusId">
                {{workStatus.workStatusNameE}}
            </a>
        </li>
    </ul>
</div>
```

**Result**: Displays buttons for each work status
- Tender Called (Status 3)
- Tender Received (Status 4)
- LoA Issued (Status 7)
- Work Order Issued (Status 8)
- Re-Tender (Status 14)

## Field 1: Tender Called Date (Lines 873-896)
```html
<div class="form-group col-sm-4" data-ng-show="workDataTender.workStatusId=='3'">
    <label>
        <span>Tender Called Date</span>
        <span class="aestrick">&#42;</span>
    </label>
    <div class="input-group date" id="datetimepicker16" data-target-input="nearest">
        <input type="text" class="form-control datetimepicker-input"
               data-ng-required="workDataTender.workStatusId=='3'"
               data-target="#datetimepicker16"
               name="tenderCalledDate"
               placeholder="DD/MM/YYYY"
               data-ng-model="workDataTender.tenderCalledDate"
               id="tenderCalledDate"
               autocomplete="off" />
        <div class="input-group-append input-group-addon"
             data-target="#datetimepicker16"
             data-toggle="datetimepicker">
            <div class="input-group-text">
                <i class="fa fa-calendar"></i>
            </div>
        </div>
    </div>
    <div data-ng-show="workTenderForm.$submitted">
        <p class="help-block" style="color: red;"
           data-ng-show="workTenderForm.tenderCalledDate.$error.required">
            <span>Please enter</span> Tender Called Date
        </p>
    </div>
</div>
```

**Visibility**: `data-ng-show="workDataTender.workStatusId=='3'"`
**Required**: `data-ng-required="workDataTender.workStatusId=='3'"`
**Model**: `workDataTender.tenderCalledDate`

## Field 2: Tender Received Date (Lines 926-959)
```html
<div class="form-group col-sm-4" data-ng-show="workDataTender.workStatusId=='4'">
    <label>
        <span>Tender Received Date</span>
        <span class="aestrick">&#42;</span>
    </label>
    <div class="input-group date" id="datetimepicker17" data-target-input="nearest">
        <input type="text" class="form-control datetimepicker-input"
               data-target="#datetimepicker17"
               name="tenderReceivedDate"
               data-ng-required="workDataTender.workStatusId=='4'"
               placeholder="DD/MM/YYYY"
               data-ng-model="workDataTender.tenderReceivedDate"
               id="tenderReceivedDate"
               autocomplete="off" />
        <div class="input-group-append input-group-addon"
             data-target="#datetimepicker17"
             data-toggle="datetimepicker">
            <div class="input-group-text">
                <i class="fa fa-calendar"></i>
            </div>
        </div>
    </div>
    <div data-ng-show="workTenderForm.$submitted">
        <p class="help-block" style="color: red;"
           data-ng-show="workTenderForm.tenderReceivedDate.$error.required">
            <span>Please enter</span> Tender Received Date
        </p>
    </div>
</div>
```

**Visibility**: `data-ng-show="workDataTender.workStatusId=='4'"`
**Required**: `data-ng-required="workDataTender.workStatusId=='4'"`
**Model**: `workDataTender.tenderReceivedDate`

## Field 3: LoA Issued Date (Lines 996-1019)
```html
<div class="form-group col-sm-4" data-ng-show="workDataTender.workStatusId=='7'">
    <label>
        <span>LoA Issued Date</span>
        <span class="aestrick">&#42;</span>
    </label>
    <div class="input-group date" id="datetimepicker21" data-target-input="nearest">
        <input type="text" class="form-control datepicker-input"
               data-target="#datetimepicker21"
               name="loaIssuedDate"
               data-ng-required="workDataTender.workStatusId=='7'"
               placeholder="DD/MM/YYYY"
               data-ng-model="workDataTender.loaIssuedDate"
               id="loaIssuedDate"
               autocomplete="off"
               data-ng-disabled="workDataTender.tenderUpdated=='8' || 
                                workDataTender.tenderCalledDate==null || 
                                workDataTender.tenderReceivedDate==null" />
        <div class="input-group-append input-group-addon"
             data-target="#datetimepicker21"
             data-toggle="datetimepicker">
            <div class="input-group-text">
                <i class="fa fa-calendar"></i>
            </div>
        </div>
    </div>
    <div data-ng-show="workTenderForm.$submitted">
        <p class="help-block" style="color: red;"
           data-ng-show="workTenderForm.loaIssuedDate.$error.required">
            <span>Please enter</span> LOA Issued Date
        </p>
    </div>
</div>
```

**Visibility**: `data-ng-show="workDataTender.workStatusId=='7'"`
**Required**: `data-ng-required="workDataTender.workStatusId=='7'"`
**Model**: `workDataTender.loaIssuedDate`
**Disabled When**: Previous dates are null

## Field 4: Work Order Issued Date (Lines 269-301)
```html
<div class="form-group col-sm-4">
    <label>
        <span>Work Order Date</span>
        <span data-ng-show="workData.isTenders == 1 || workData.isTenders == true"
              class="aestrick">&#42;</span>
    </label>
    <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
        <input type="text" class="form-control datetimepicker-input"
               data-target="#datetimepicker2"
               name="workOrderDate"
               data-ng-required="(workData.isTenders == 1 || workData.isTenders == true) &amp;&amp; 
                                 (workDataTender.workStatusId=='8')"
               placeholder="DD/MM/YYYY"
               data-ng-model="workDataTender.workOrderDate"
               id="workOrderDate"
               autocomplete="off"
               data-ng-disabled="(workData.isTenders == 1 || workData.isTenders == true) &amp;&amp; 
                                 (workDataTender.tenderCalledDate==null || 
                                  workDataTender.tenderReceivedDate==null || 
                                  workDataTender.loaIssuedDate==null)" />
        <div class="input-group-append input-group-addon"
             data-target="#datetimepicker2"
             data-toggle="datetimepicker">
            <div class="input-group-text">
                <i class="fa fa-calendar"></i>
            </div>
        </div>
    </div>
    <div data-ng-show="workTenderForm.$submitted">
        <div data-ng-show="workDataTender.workStatusId=='8'">
            <p class="help-block" style="color: red;"
               data-ng-show="workTenderForm.workOrderDate.$error.required">
                <span>Please enter</span> Work Order Date
            </p>
        </div>
    </div>
</div>
```

**Visibility**: Shown for Status 8, 9, 10, 11, 12, 13
**Required**: When Status = 8 AND isTenders = 1
**Model**: `workDataTender.workOrderDate`
**Disabled When**: Previous dates are null

## Field 5: Re-Tender Date (Lines 960-982)
```html
<div class="form-group col-sm-4" data-ng-show="workDataTender.workStatusId=='14'">
    <label>
        <span>Re-Tender Date</span>
    </label>
    <div class="input-group date" id="datetimepicker18" data-target-input="nearest">
        <input type="text" class="form-control datetimepicker-input"
               data-target="#datetimepicker18"
               name="reTenderDate"
               placeholder="DD/MM/YYYY"
               data-ng-model="workDataTender.reTenderDate"
               id="reTenderDate"
               autocomplete="off" />
        <div class="input-group-append input-group-addon"
             data-target="#datetimepicker18"
             data-toggle="datetimepicker">
            <div class="input-group-text">
                <i class="fa fa-calendar"></i>
            </div>
        </div>
    </div>
    <div data-ng-show="workTenderForm.$submitted">
        <p class="help-block" style="color: red;"
           data-ng-show="workTenderForm.reTenderDate.$error.required">
            <span>Please enter</span> Re-Tender Date
        </p>
    </div>
</div>
```

**Visibility**: `data-ng-show="workDataTender.workStatusId=='14'"`
**Required**: Yes (marked with *)
**Model**: `workDataTender.reTenderDate`

## Additional Fields for Status 8

### Security Deposit (Lines 1030-1040)
```html
<div class="form-group col-sm-4">
    <label><span>Security Deposit</span></label>
    <label class="radio-inline" style="padding-right: 5px;">
        <input type="radio" name="secureAmtStatus" checked="checked"
               data-ng-model="workDataTender.secureAmtStatus" value="BG" />
        <span>BG</span>
    </label>
    <label class="radio-inline">
        <input type="radio" name="secureAmtStatus"
               data-ng-model="workDataTender.secureAmtStatus" value="FDR" />
        <span>FDR</span>
    </label>
</div>
```

### BG/FDR Start Date (Lines 1043-1080)
```html
<div class="form-group col-sm-4">
    <label><span>BG/FDR Start Date</span></label>
    <div class="input-group date" id="datetimepicker9" data-target-input="nearest">
        <input type="text" class="form-control datetimepicker-input"
               data-target="#datetimepicker9"
               name="startDate"
               placeholder="DD/MM/YYYY"
               data-ng-model="workDataTender.startDate"
               id="startDate"
               autocomplete="off"
               data-ng-disabled="workDataTender.tenderCalledDate==null || 
                                workDataTender.tenderReceivedDate==null || 
                                workDataTender.loaIssuedDate==null" />
        <div class="input-group-append input-group-addon"
             data-target="#datetimepicker9"
             data-toggle="datetimepicker">
            <div class="input-group-text">
                <i class="fa fa-calendar"></i>
            </div>
        </div>
    </div>
</div>
```

## File Upload Fields (Status 8)

### Work Order File (Lines 310-347)
```html
<div class="form-group col-sm-4 mb5">
    <label>
        <span>Upload Work Order.</span>
        <span data-ng-show="workData.isTenders == 1" class="aestrick">&#42;</span>
    </label>
    <input type="file" name="ldTdfFile" id="ldTdfFile"
           data-ng-model="ldTdfFile"
           accept=".pdf,.doc,.docx,.xls,.xlsx,.jpg,.png"
           data-ng-change="fileUploadWorkOrder(ldTdfFile)" />
</div>
```

### Technical Drawing File (Lines 350-398)
```html
<div class="form-group col-sm-4 mb5">
    <label>
        <span>Upload Technical Drawing.</span>
        <span data-ng-show="workData.isTenders == 1" class="aestrick">&#42;</span>
    </label>
    <input type="file" name="dTTTFile" id="dTTTFile"
           data-ng-model="dTTTFile"
           accept=".pdf,.doc,.docx,.xls,.xlsx,.jpg,.png"
           data-ng-change="fileUploadTechnicalDrawing(dTTTFile)" />
</div>
```

### Tender File (Lines 401-449)
```html
<div class="form-group col-sm-4 mb5">
    <label>
        <span>Upload Tender.</span>
        <span data-ng-show="workData.isTenders == 1" class="aestrick">&#42;</span>
    </label>
    <input type="file" name="ldTULFile" id="ldTULFile"
           data-ng-model="ldTULFile"
           accept=".pdf,.doc,.docx,.xls,.xlsx,.jpg,.png"
           data-ng-change="fileUploadTender(ldTULFile)" />
</div>
```

### Agreement File (Lines 452-500)
```html
<div class="form-group col-sm-4 mb5">
    <label>
        <span>Upload Agreement.</span>
        <span data-ng-show="workData.isTenders == 1" class="aestrick">&#42;</span>
    </label>
    <input type="file" name="ldUAFile" id="ldUAFile"
           data-ng-model="ldUAFile"
           accept=".pdf,.doc,.docx,.xls,.xlsx,.jpg,.png"
           data-ng-change="fileUploadAgreement(ldUAFile)" />
</div>
```

## Form Submission (Line 200)
```html
<form data-ng-submit="createTenderAgreementData(workTenderForm, 
                                               workTenderForm.$valid,
                                               ldTdfFile,
                                               dTTTFile,
                                               ldTULFile,
                                               ldUAFile);"
      name="workTenderForm"
      id="workFormId"
      novalidate="novalidate"
      enctype="multipart/form-data">
```

## Angular Model Object
```javascript
$scope.workDataTender = {
    workId: Long,
    workStatusId: String,           // 3, 4, 7, 8, 14
    tenderCalledDate: Date,         // Status 3
    tenderReceivedDate: Date,       // Status 4
    loaIssuedDate: Date,            // Status 7
    workOrderDate: Date,            // Status 8
    reTenderDate: Date,             // Status 14
    secureAmtStatus: String,        // BG or FDR (Status 8)
    startDate: Date,                // BG/FDR Start Date (Status 8)
    count: Number,                  // Re-Tender Count (Status 8)
    workStatusNameE: String,        // Current status name
    tenderUpdated: String           // Tender update status
}
```

## Summary

All 5 tender date fields are present in the HTML:
1. ✅ Tender Called Date (Status 3)
2. ✅ Tender Received Date (Status 4)
3. ✅ LoA Issued Date (Status 7)
4. ✅ Work Order Issued Date (Status 8)
5. ✅ Re-Tender Date (Status 14)

They are controlled by Angular directives and appear/disappear based on the selected Work Status.
