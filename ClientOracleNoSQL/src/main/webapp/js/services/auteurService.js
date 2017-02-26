/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Services de la gestion des projets.
 * Principalement utilisés pour communiquer avec l'API REST exposée par le back-end (l'application Java gérant le métier et la persistance).
 */

oraclenosqlServices.factory('auteurMainFactory', ['$http', '$q', '$log', function ($http, $q, $log) {

        return {

            // lecture de la liste des auteurs
            readAuteurs: function () {
                var deferred = $q.defer();
                $log.debug('auteurMainFactory - reading auteurs : appel au serveur ...');
                $http({
                    method: 'GET',
                    url: 'http://localhost:8080/ServerOracleNoSQL/ws/auteur'
                }).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Erreur - Impossible de récupérer les auteurs.');
                    $log.debug('... réponse du serveur : erreur.');
                });
                return deferred.promise;
            },

            // création d'un auteur
            createAuteur: function (id, nom, prenom, adresse, phone, fax, mail) {
                var data = {"id": id, "nom": nom, "prenom": prenom, "adresse": adresse, "phone": phone, "fax": fax, "mail": mail};
                var deferred = $q.defer();

                $http.post('http://localhost:8080/ServerOracleNoSQL/ws/auteur', JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    $log.debug('***********************************************');
                    $log.debug(response);
                    $log.debug('***********************************************');
                    // deferred.reject('Impossible de créer l\'auteur');
                    deferred.reject(response.data);
                });
                return deferred.promise;
            },
            
            modifyAuteur: function (id, nom, prenom, adresse, phone, fax, mail) {

                var data = {"id": id, "nom": nom, "prenom": prenom, "adresse": adresse, "phone": phone, "fax": fax, "mail": mail};
                var deferred = $q.defer();
                $http.put('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id, JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    $log.debug('***********************************************');
                    $log.debug(response);
                    $log.debug('***********************************************');
                    deferred.reject('Impossible de mettre a jour l\'auteur');
                    deferred.reject(response.data);
                });
                return deferred.promise;
            },
            
            removeAuteur: function (id) {
                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'auteur');
                    deferred.reject(response.data);
                });
            },
            
            readArticles: function (id) {
                var deferred = $q.defer();
                $log.debug('articleMainFactory - reading authors from article ' + id + ': appel au serveur ...');
                $http({
                    method: 'GET',
                    url: 'http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id + '/article'
                }).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Erreur - Impossible de récupérer les auteurs.');
                    $log.debug('... réponse du serveur : erreur.');
                });
                return deferred.promise;
            },
            
            readUniversites: function (id) {
                var deferred = $q.defer();
                $log.debug('articleMainFactory - reading universities from author ' + id + ': appel au serveur ...');
                $http({
                    method: 'GET',
                    url: 'http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id + '/university'
                }).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Erreur - Impossible de récupérer les universités.');
                    $log.debug('... réponse du serveur : erreur.');
                });
                return deferred.promise;
            },
            
            readLaboratoires: function (id) {
                var deferred = $q.defer();
                $log.debug('articleMainFactory - reading laboratories from author ' + id + ': appel au serveur ...');
                $http({
                    method: 'GET',
                    url: 'http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id + '/laboratoire'
                }).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Erreur - Impossible de récupérer les laboratoires.');
                    $log.debug('... réponse du serveur : erreur.');
                });
                return deferred.promise;
            },
            
            readKeywords: function (id) {
                var deferred = $q.defer();
                $log.debug('articleMainFactory - reading keywords from article ' + id + ': appel au serveur ...');
                $http({
                    method: 'GET',
                    url: 'http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id + '/keyword'
                }).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Erreur - Impossible de récupérer les auteurs.');
                    $log.debug('... réponse du serveur : erreur.');
                });
                return deferred.promise;
            }
        };
    }]);