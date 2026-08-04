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

	function createDocumentActionButton(title, iconClass, action, documentId) {
		return $('<button>', {
			type: 'button',
			'class': 'btn btn-xs btn-warning',
			title: title
		})
			.attr('data-edit-work-action', action)
			.attr('data-document-id', Number(documentId))
			.append($('<i>', { 'class': iconClass }));
	}

	function getEditWorkScope(selector) {
		var element = document.querySelector(selector);
		var scope = element ? angular.element(element).scope() : null;
		if (scope) {
			return scope;
		}
		var view = document.querySelector('[data-ng-view], [ng-view]');
		return view ? angular.element(view).scope() : null;
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
				var actionCell = $('td:eq(7)', nRow).empty();
				if (aData.imagepath && aData.imagepath !== '') {
					var progressLabel = aData.workSubStatusNameE || aData.reasonDelay || '';
					if (aData.workStatusId == 10) {
						actionCell.append(createDocumentActionButton(
							'Download File', 'fa fa-download', 'download-progress-document', aData.documentId));
					}
					var viewButton = createDocumentActionButton(
						'View File', 'fa fa-eye', 'view-progress-document', aData.documentId);
					viewButton.data('progress-label', progressLabel);
					actionCell.append(viewButton);
				}
				if (aData.workStatusId == 9) {
					actionCell.html('<span>-</span>');
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
		var scope = getEditWorkScope('#page-wp-div');
		if (!scope || typeof scope.downloadDocumentWSPro !== 'function') {
			console.error('Unable to download progress document: Angular scope not found');
			return false;
		}
		return scope.downloadDocumentWSPro(fileId);
	};

	window.downloadFileWSDocumnt = function(fileId, workSubStatusNameE) {
		var scope = getEditWorkScope('#page-wp-div');
		if (!scope || typeof scope.downloadDocumentIdWSPro !== 'function') {
			console.error('Unable to view progress document: Angular scope not found');
			return false;
		}
		return scope.downloadDocumentIdWSPro(fileId, workSubStatusNameE);
	};

	$(document)
		.off('click.editWorkDocuments', '[data-edit-work-action]')
		.on('click.editWorkDocuments', '[data-edit-work-action]', function(event) {
			event.preventDefault();
			var action = $(this).attr('data-edit-work-action');
			var documentId = Number($(this).attr('data-document-id'));
			if (!Number.isFinite(documentId) || documentId <= 0) {
				console.error('Invalid progress document id');
				return;
			}
			if (action === 'download-progress-document') {
				window.downloadFileWS(documentId);
			} else if (action === 'view-progress-document') {
				window.downloadFileWSDocumnt(documentId, $(this).data('progress-label') || '');
			}
		});

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
