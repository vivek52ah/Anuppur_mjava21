/**
 * DataTable loaders for Edit Work -> Work Progress tab.
 * Loaded globally from footer so AJAX fragment (editWork-fragment) can use them.
 */
(function(window, $) {
	'use strict';

	function getAjaxBase() {
		if (window.__BASE_HREF_AJAX_BASE) {
			return window.__BASE_HREF_AJAX_BASE;
		}
		var pagePath = window.location.pathname || '';
		return pagePath.substring(0, pagePath.lastIndexOf('/') + 1);
	}

	function resolveUrl(path) {
		if (!path) {
			return path;
		}
		if (/^(\/|https?:\/\/|data:|#)/.test(path)) {
			return path;
		}
		return getAjaxBase() + path;
	}

	function ensureDataTable() {
		return $ && $.fn && typeof $.fn.DataTable === 'function';
	}

	window.fetchProgressImagesList = function(workId) {
		if (!workId || !ensureDataTable()) {
			console.error('DataTables not available or missing workId for progress list');
			return;
		}
		if ($.fn.DataTable.isDataTable('#dynamic-wp-table')) {
			$('#dynamic-wp-table').DataTable().clear().destroy();
		}
		if (!$('#dynamic-wp-table').length) {
			return;
		}

		window.t = $('#dynamic-wp-table').DataTable({
			processing: true,
			serverSide: true,
			sort: 'position',
			bStateSave: false,
			iDisplayLength: 10,
			bInfo: false,
			searching: false,
			iPage: false,
			iDisplayStart: 0,
			pagingType: 'full_numbers',
			aaSorting: [],
			fnCreatedRow: function(nRow, aData) {
				$('td:eq(7)', nRow).html('');
				if (aData.imagepath && aData.imagepath !== '') {
					var progressLabel = aData.workSubStatusNameE || aData.reasonDelay || '';
					if (aData.workStatusId == 10) {
						$('td:eq(7)', nRow).html(
							'<a class="btn btn-xs btn-warning" data-toggle="tooltip" data-placement="top" title="Download File" onclick="return downloadFileWS('
								+ aData.documentId + ');"><i class="fa fa-download"></i></a>'
						);
					}
					$('td:eq(7)', nRow).append(
						'<button class="btn btn-xs btn-warning" type="button" title="Download File" onclick="return downloadFileWSDocumnt('
							+ aData.documentId + ',\'' + progressLabel.replace(/'/g, "\\'") + '\')"> <i class="fa fa-eye"></i></button>'
					);
				}
				if (aData.workStatusId == 9) {
					$('td:eq(7)', nRow).html('<span>-</span>');
				}
			},
			sAjaxSource: resolveUrl('fetchProgressImagesList/' + workId),
			columns: [
				{ data: 'indexWS', bSortable: false },
				{ data: 'workStatusNameE', bSortable: false },
				{ data: 'workSubStatusNameE', bSortable: false },
				{ data: 'reasonDelay', bSortable: false },
				{ data: 'actionTakenDelay', bSortable: false },
				{
					data: 'remarks',
					bSortable: false,
					render: function(data, type, row) {
						return row.moreImage === true ? '--' : data;
					}
				},
				{ data: 'createdDate', bSortable: false },
				{ data: null, bSortable: false }
			]
		});
	};

	window.fetchExpensesDataList = function(workId) {
		if (!workId || !ensureDataTable()) {
			console.error('DataTables not available or missing workId for expenses list');
			return;
		}
		if ($.fn.DataTable.isDataTable('#dynamic-ex-table')) {
			$('#dynamic-ex-table').DataTable().clear().destroy();
		}
		if (!$('#dynamic-ex-table').length) {
			return;
		}

		window.ex = $('#dynamic-ex-table').DataTable({
			processing: true,
			serverSide: true,
			sort: 'position',
			bStateSave: false,
			iDisplayLength: 10,
			bInfo: false,
			searching: false,
			iPage: true,
			iDisplayStart: 0,
			pagingType: 'full_numbers',
			aaSorting: [],
			fnServerParams: function(aoData) {
				var yearVal = $('#year').val();
				if (yearVal) {
					aoData.push({ name: 'year', value: yearVal });
				}
				console.log('Server params:', aoData);
			},
			sAjaxSource: resolveUrl('fetchExpensesDataList/' + workId),
			fnServerData: function(sSource, aoData, fnCallback) {
				console.log('Fetching from:', sSource);
				$.ajax({
					dataType: 'json',
					type: 'GET',
					url: sSource,
					data: aoData,
					success: function(json) {
						console.log('Expenses data received:', json);
						if (!json) {
							json = { iTotalRecords: 0, iTotalDisplayRecords: 0, aaData: [] };
						}
						if (json.iTotalDisplayRecords == null || json.iTotalDisplayRecords === 0) {
							json.iTotalDisplayRecords = (json.aaData && json.aaData.length) ? json.aaData.length : 0;
						}
						if (json.iTotalRecords == null) {
							json.iTotalRecords = json.iTotalDisplayRecords;
						}
						console.log('Processed data:', json);
						fnCallback(json);
					},
					error: function(xhr, status, error) {
						console.error('Error fetching expenses:', error, xhr);
						fnCallback({ iTotalRecords: 0, iTotalDisplayRecords: 0, aaData: [] });
					}
				});
			},
			columns: [
				{ data: 'index', bSortable: false },
				{ data: 'totalExpensess', bSortable: false, render: function(data) { return data || '0'; } },
				{ data: 'expensessCurrentFy', bSortable: false, render: function(data) { return data || '0'; } },
				{ data: 'expensessUptoMarch', bSortable: false, render: function(data) { return data || '0'; } },
				{ data: 'year', bSortable: false },
				{ data: 'createdDate', bSortable: false }
			]
		});
	};

	window.exDraw = function() {
		if (window.ex) {
			window.ex.draw();
		}
	};

	window.fetchFinancialAgency = function(workId) {
		if (!workId || !ensureDataTable()) {
			console.error('DataTables not available or missing workId for financial agency list');
			return;
		}
		if ($.fn.DataTable.isDataTable('#dynamic-fa-table')) {
			$('#dynamic-fa-table').DataTable().clear().destroy();
		}
		if (!$('#dynamic-fa-table').length) {
			return;
		}

		$('#dynamic-fa-table').DataTable({
			processing: true,
			serverSide: true,
			searching: false,
			pagingType: 'full_numbers',
			aaSorting: [],
			ajax: {
				url: resolveUrl('fetchFinancialAgency/' + workId),
				type: 'GET',
				dataSrc: function(json) {
					return (json && json.data) ? json.data : [];
				}
			},
			columns: [
				{
					data: null,
					bSortable: false,
					render: function(data, type, row, meta) {
						return meta.row + meta.settings._iDisplayStart + 1;
					}
				},
				{ data: 'financialAgencyName', bSortable: false },
				{ data: 'cost', bSortable: false },
				{ data: 'expenditure', bSortable: false },
				{ data: 'balance', bSortable: false },
				{
					data: 'id',
					bSortable: false,
					render: function(data, type, row) {
						return '<input type="text" class="form-control cost-input" data-id="' + data
							+ '" data-workid="' + row.workId
							+ '" data-financialheadid="' + row.financialHeadId
							+ '" data-cost="' + row.cost + '" />';
					}
				}
			],
			initComplete: function(settings, json) {
				var total = 0;
				if (json && json.totalExpenditure != null) {
					total = parseFloat(json.totalExpenditure) || 0;
				} else if (json && json.data) {
					json.data.forEach(function(row) {
						var val = parseFloat(row.expenditure);
						if (!isNaN(val)) {
							total += val;
						}
					});
				}
				var scope = angular.element('#page-fa-div').scope();
				if (scope) {
					scope.$apply(function() {
						scope.workDataProgress.totalExpensess = parseFloat(total.toFixed(2));
						scope.workDataProgress.baseTotalExpensess = parseFloat(total.toFixed(2));
					});
				}
			}
		});
	};

	window.openDiv = function(workId, createdDate) {
		var div = $('#myDiv');
		angular.element('#dynamic-wp-table').scope().fetchImageByCreatedDate(createdDate);
		div.show();
		return false;
	};

	window.closeDiv = function() {
		$('#myDiv').hide();
	};

	window.downloadFileWS = function(fileId) {
		return $('#page-wp-div').scope().downloadDocumentWSPro(fileId);
	};

	window.downloadFileWSDocumnt = function(fileId, workSubStatusNameE) {
		return $('#page-wp-div').scope().downloadDocumentIdWSPro(fileId, workSubStatusNameE);
	};

	$(document).off('input.editWorkCost', '.cost-input').on('input.editWorkCost', '.cost-input', function() {
		var newExp = 0;
		$('.cost-input').each(function() {
			newExp += parseFloat($(this).val()) || 0;
		});
		var scope = angular.element('#page-fa-div').scope();
		if (scope && scope.workDataProgress && scope.workDataProgress.baseTotalExpensess !== undefined) {
			scope.$apply(function() {
				scope.workDataProgress.totalExpensess = parseFloat((scope.workDataProgress.baseTotalExpensess + newExp).toFixed(2));
				scope.workDataProgress.expensessCurrentFy = parseFloat(newExp.toFixed(2));
			});
		}
	});

})(window, window.jQuery);
