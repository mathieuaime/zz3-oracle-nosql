google.load('visualization', '1.0', {'packages' : [ 'corechart' ]});


oraclenosql.directive('tabs', function() {
  return function link(scope, tabsElement) {
  
  };
});
oraclenosql.directive('resizable', function () {
	
    return {
        restrict: 'A',
        scope: {
            callback: '&onResize'
        },
        link: function postLink(scope, elem, attrs) {
            elem.resizable();
            elem.on('resize', function (event, ui) {
              scope.$apply(function() {
                if (scope.callback) { 
                  scope.callback({$event: event, $ui: ui }); 
                }                
              })
            });
        }
    };
});

oraclenosql.directive('pieChart', function($timeout, $log) {
	
	return {
		
		restrict : 'EA',
		
		scope : {
			// title : '@title',
			width : '=width',
			height : '=height',
			data : '=data',
			selectFn : '&select'
		},
		
		link : function($scope, $elm, $attr) {

			// Create the data table and instantiate the chart
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'Label');
			data.addColumn('number', 'Value');
			var chart = new google.visualization.PieChart($elm[0]);
			draw();

			// Watches, to refresh the chart when its data, title or dimensions change
			$scope.$watch('data', function() { draw(); }, true); // true is for deep object equality checking
			// $scope.$watch('title', function() {	draw();	});
			$scope.$watch('width', function() {	draw();	});
			$scope.$watch('height', function() { draw(); });

			// Chart selection handler
			google.visualization.events.addListener(chart, 'select', function() {
				var selectedItem = chart.getSelection()[0];
				if (selectedItem) {
					$scope.$apply(function() {
						$scope.selectFn({
							selectedRowIndex : selectedItem.row
						});
					});
				}
			});

			// Chart rendering
			function draw() {
				if (!draw.triggered) {
					draw.triggered = true;
					$timeout(function() {
						draw.triggered = false;
						data.removeRows(0, data.getNumberOfRows());
						var colors = new Array();
						angular.forEach($scope.data, function(row) {
							var value = parseFloat(row[1], 10);
							if (!isNaN(value)) {
								data.addRow([ row[0], value,  ]);
							}
							colors.push(row[2]);
						});
						var options = {
							// title : $scope.title,
							// is3D: true,
							pieHole: 0.3, // donut
							sliceVisibilityThreshold: 0.05,
							width : $scope.width - 30,
							height : $scope.height - 70,
							chartArea: {width:'94%', height:'94%'},
							fontName: 'titillium',
							legend: {position: 'labeled', textStyle: {color: 'black', fontName: 'titillium'}},
							pieSliceText: 'none',
							colors: colors
						};
						chart.draw(data, options);
						// No raw selected
						$scope.selectFn({
							selectedRowIndex : undefined
						});
					}, 0, true);
				}
			}
		}
	};
});