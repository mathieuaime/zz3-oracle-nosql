/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.module('myApp', ['ngResource','ngGrid', 'ui.bootstrap']);

app.controller('listMedecinController', function($scope, $rootScope, $http, medecinService) {
    
    $scope.myData;
    
    $scope.gridMedecin = {
        data: 'myData',
        
        multiSelect: false,
        
        columnDefs: [
            { field: 'id', displayName: 'Id', width: 50 },
            { field: 'name', displayName: 'Nom' },
            { field: 'firstName', displayName: 'Prénom' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }
        ],
        
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('medecinSelected', $scope.gridMedecin.selectedItems[0].id);
            }
        }
    };
    
    $scope.refreshGrid = function() {
        $http.get('ws/medecin').then(function (response) {
            $scope.myData = response.data;
        });
    };
    
    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteMedecin', row.entity.id);
    };
    
    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });
    
    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clearM', function () {
        $scope.gridMedecin.selectAll(false);
    });
    
    $scope.refreshGrid();
});

app.controller('medecinFormController', function ($scope, $rootScope, $http, medecinService) {
    
    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.medecin = null;
        // Resets the form validation state.
        $scope.medecinForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clearM');
    };
    
    // Calls the rest method to save a person.
    $scope.updateMedecin = function () {
        if($scope.medecin.id === undefined) { //add
            medecinService.save($scope.medecin,
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('personSaved');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
                
        } else { //edit
            medecinService.update({ id:$scope.medecin.id }, $scope.medecin,
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('personUpdated');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });           
        }
    };
    
    // Picks up the event broadcasted when the person is selected from the grid and perform the person load by calling
    // the appropiate rest service.
    $scope.$on('medecinSelected', function (event, id) {
        $scope.medecin = medecinService.get({id: id});
    });
    
    // Picks us the event broadcasted when the person is deleted from the grid and perform the actual person delete by
    // calling the appropiate rest service.
    $scope.$on('deleteMedecin', function (event, id) {
        medecinService.delete({id: id}).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a delete message.
                $rootScope.$broadcast('personDeleted');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    });
    
});

app.controller('listPatientController', function($scope, $rootScope, $http) {
    
    $scope.myData;
    
    $scope.gridPatient = {
        data: 'myData',
        
        multiSelect: false,
        
        columnDefs: [
            { field: 'id', displayName: 'Id', width: 50 },
            { field: 'name', displayName: 'Nom' },
            { field: 'firstName', displayName: 'Prénom' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }
        ],
        
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('patientSelected', $scope.gridPatient.selectedItems[0].id);
            }
        }
    };
    
    $scope.refreshGrid = function() {
        $http.get('ws/patient').then(function (response) {
            $scope.myData = response.data;
        });
    };
    
    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deletePatient', row.entity.id);
    };
    
    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });
    
    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clearP', function () {
        $scope.gridPatient.selectAll(false);
    });
    
    $scope.refreshGrid();
});

app.controller('patientFormController', function ($scope, $rootScope, $http, patientService) {
    
    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.patient = null;
        // Resets the form validation state.
        $scope.patientForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clearP');
    };
    
    // Calls the rest method to save a person.
    $scope.updatePatient = function () {
        if($scope.patient.id === undefined) { //add
            patientService.save($scope.patient,
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('personSaved');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
                
        } else { //edit
            patientService.update({ id:$scope.patient.id }, $scope.patient,
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('personUpdated');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });             
        }
    };
    
    // Picks up the event broadcasted when the person is selected from the grid and perform the person load by calling
    // the appropiate rest service.
    $scope.$on('patientSelected', function (event, id) {
        $scope.patient = patientService.get({id: id});
    });
    
    // Picks us the event broadcasted when the person is deleted from the grid and perform the actual person delete by
    // calling the appropiate rest service.
    $scope.$on('deletePatient', function (event, id) {
        patientService.delete({id: id}).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a delete message.
                $rootScope.$broadcast('personDeleted');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    });
    
});

app.controller('listCreneauController', function($scope, $rootScope, $http) {
    
    $scope.myData;
    
    $scope.gridCreneau = {
        data: 'myData',
        
        multiSelect: false,
        
        columnDefs: [
            { field: 'id', displayName: 'Id', width: 50 },
            { field: 'begin', displayName: 'Début', cellFilter: 'date:\'dd/MM/yyyy HH:mm\'' },
            { field: 'end', displayName: 'Fin', cellFilter: 'date:\'dd/MM/yyyy HH:mm\''  },
            { field: 'fullNameMedecin()', displayName: 'Médecin' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }
        ],
        sortInfo: {
            fields: ['begin'],
            directions: ['asc']
        },
        
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('creneauSelected', $scope.gridCreneau.selectedItems[0].id);
            }
        }
    };
    
    $scope.refreshGrid = function() {
        $http.get('ws/creneau/free').then(function (response) {
            $scope.myData = response.data;
            angular.forEach($scope.myData,function(row){
                row.fullNameMedecin = function(){
                  return this.medecin.name + ' ' + this.medecin.firstName;
                };
            });
        });
    };
    
    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteCreneau', row.entity.id);
    };
    
    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });
    
    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clearC', function () {
        $scope.gridCreneau.selectAll(false);
    });
    
    $scope.refreshGrid();
});

app.controller('creneauFormController', function ($scope, $rootScope, $http, creneauService, addCreneauService) {
    
    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.creneau = null;
        $scope.patient = null;
        // Resets the form validation state.
        $scope.creneauForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clearC');
    };
    
    // Calls the rest method to save a person.
    $scope.updateCreneau = function () {
        if($scope.creneau.id === undefined) { //add
            alert('add');
            addCreneauService.save({id:$scope.medecin.id}, $scope.creneau,
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('creneauSaved');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
                
        } else { //edit
            creneauService.update({ id:$scope.creneau.id }, $scope.creneau,
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('creneauUpdated');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });             
        }
    };
    
    $scope.addRDV = function () {
        creneauService.save({ id:$scope.creneau.id, action:"reserver"}, $scope.patient,
        function() {
            $rootScope.$broadcast('refreshGrid');
            $rootScope.$broadcast('rdvSaved');
            $scope.clearForm();
        },
        function() {
            // Broadcast the event for a server error.
            $rootScope.$broadcast('error');
        });
    };
    
    $scope.changePatient = function (patient) {
        $scope.patient = patient;
    };
    
    $scope.changeMedecin = function (medecin) {
        $scope.medecin = medecin;
    };
    
    // Picks up the event broadcasted when the person is selected from the grid and perform the person load by calling
    // the appropiate rest service.
    $scope.$on('creneauSelected', function (event, id) {
        $http.get('ws/creneau/'+id).then(function (response) {
            $scope.creneau = response.data;
            $scope.medecin = response.data.medecin;            
        }); 
    });
    
    // Picks us the event broadcasted when the person is deleted from the grid and perform the actual person delete by
    // calling the appropiate rest service.
    $scope.$on('deleteCreneau', function (event, id) {
        
        creneauService.delete({id: id}).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a delete message.
                $rootScope.$broadcast('creneauDeleted');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    });
    
    $http.get('ws/medecin').then(function (response) {
        $scope.medecins = response.data;
    }); 
    
    $http.get('ws/patient').then(function (response) {
        $scope.patients = response.data;
    }); 
    
});

app.controller('listRDVController', function($scope, $rootScope, $http) {
    
    $scope.myData;
    
    $scope.gridRDV = {
        data: 'myData',
        
        multiSelect: false,
        
        columnDefs: [
            { field: 'id', displayName: 'Id', width: 50 },
            { field: 'creneau.begin', displayName: 'Date', cellFilter: 'date:\'dd/MM/yyyy HH:mm\'' },
            { field: 'fullNamePatient()', displayName: 'Patient'},
            { field: 'fullNameMedecin()', displayName: 'Medecin' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }
        ],
        sortInfo: {
            fields: ['date'],
            directions: ['asc']
        },
        
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('rdvSelected', $scope.gridRDV.selectedItems[0].id);
            }
        }
    };
    
    $scope.refreshGrid = function() {
        $http.get('ws/rdv').then(function (response) {
            $scope.myData = response.data;
            angular.forEach($scope.myData,function(row){
                row.fullNameMedecin = function(){
                  return this.creneau.medecin.name + ' ' + this.creneau.medecin.firstName;
                };
                row.fullNamePatient = function(){
                    return this.patient.name + ' ' + this.patient.firstName;
                };
            });
        });
    };
    
    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteRDV', row.entity.creneau.id);
    };
    
    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });
    
    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clearR', function () {
        $scope.gridRDV.selectAll(false);
    });
    
    $scope.refreshGrid();
});

app.controller('rdvFormController', function ($scope, $rootScope, $http, rdvService, creneauService) {
    
    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.rdv = null;
        // Resets the form validation state.
        $scope.rdvForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clearR');
    };
    
    // Calls the rest method to save a person.
    $scope.updateRDV = function () {
        rdvService.update({ id:$scope.rdv.id }, $scope.rdv,
        function () {
            // Broadcast the event to refresh the grid.
            $rootScope.$broadcast('refreshGrid');
            $rootScope.$broadcast('rdvUpdated');
            $scope.clearForm();
        },
        function () {
            // Broadcast the event for a server error.
            $rootScope.$broadcast('error');
        });        
    };
    
    $scope.changePatient = function (patient) {
        $scope.rdv.patient = patient;
    };
    
    $scope.changeCreneau = function (creneau) {
        $scope.rdv.creneau = creneau;
    };
    
    // Picks up the event broadcasted when the person is selected from the grid and perform the person load by calling
    // the appropiate rest service.
    $scope.$on('rdvSelected', function (event, id) {
        $http.get('ws/rdv/'+id).then(function (response) {
            $scope.rdv = response.data;
            $scope.rdv.creneau = response.data.creneau;
            $scope.rdv.patient = response.data.patient;
            
            $http.get('ws/patient').then(function (response) {
                $scope.patients = response.data;
            }); 
            
            $http.get('ws/creneau/free').then(function (response) {
                $scope.creneaux = response.data;
                $scope.creneaux.push($scope.rdv.creneau);
            }); 
            
        }); 
        
    });
    
    // Picks us the event broadcasted when the person is deleted from the grid and perform the actual person delete by
    // calling the appropiate rest service.
    $scope.$on('deleteRDV', function (event, id) {//a refaire -> annuler rdv
        creneauService.save({ id:id, action:"annuler" },null,
        function() {
            $rootScope.$broadcast('refreshGrid');
            $rootScope.$broadcast('rdvDeleted');
            $scope.clearForm();
        },
        function() {
            // Broadcast the event for a server error.
            $rootScope.$broadcast('error');
        });
    });
    
});
// Create a controller with name alertMessagesController to bind to the feedback messages section.
app.controller('alertMessagesController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('personSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record saved successfully!' }
        ];
    });
    
    // Picks up the event to display a saved message.
    $scope.$on('personUpdated', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record updated successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('personDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record deleted successfully!' }
        ];
    });
    
    $scope.$on('creneauSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Creneau saved successfully!' }
        ];
    });
    
    // Picks up the event to display a saved message.
    $scope.$on('creneauUpdated', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Creneau updated successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('creneauDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Creneau deleted successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('rdvSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'RDV saved successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('rdvUpdated', function () {
        $scope.alerts = [
            { type: 'success', msg: 'RDV updated successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('rdvDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'RDV deleted successfully!' }
        ];
    });

    // Picks up the event to display a server error message.
    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

// Service that provides medecins operations
app.factory('medecinService', function ($resource) {
    return $resource('ws/medecin/:id', null, { 'update': {method: 'PUT'} });
});

// Service that provides patients operations
app.factory('patientService', function ($resource) {
    return $resource('ws/patient/:id', null, { 'update': {method: 'PUT'} });
});

// Service that provides creneaux operations
app.factory('creneauService', function ($resource) {
    return $resource('ws/creneau/:id/:action', null, { 'update': {method: 'PUT'} });
});

// Service that provides creneaux operations
app.factory('addCreneauService', function ($resource) {
    return $resource('ws/medecin/:id/creneau', null, { 'update': {method: 'PUT'} });
});

// Service that provides rdv operations
app.factory('rdvService', function ($resource) {
    return $resource('ws/rdv/:id', null, { 'update': {method: 'PUT'} });
});

app.filter('toDate', function() {
    return function(input) {
        return new Date(input);
    };
});