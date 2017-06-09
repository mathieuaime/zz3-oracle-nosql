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

            //lecture d'un auteur :
            getAuteur: function (id) {
                var deferred = $q.defer();

                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id).then(function successCallback(response) {
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;

            },

            //Obtention de la liste des articles d'un auteur à partir de son id :
            getArticleFromAuteur: function (id) {
                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id + '/article').then(function successCallback(response) {
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },

            //obtenir la liste des articles d'un auteur à partir du titre
            getArticleFromAuteurNom: function (nom) {
                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + nom + '/articleFromName').then(function successCallback(response) {
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
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

            //creation relation A ecrit à partir de l'id de l'auteur
            createAEcritFromId: function (idArticle, idAuteur) {
                var data = {
                    "idArticle": idArticle
                };
                var deferred = $q.defer();
                $http.post('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + idAuteur + '/article', JSON.stringify(data)).then(
                        function successCallback(response) {
                            $log.debug('... réponse du serveur : OK.');
                            deferred.resolve(response.data);
                        }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },
            //creation relation A ecrit à partir de l'id de l'auteur
            createAEcritFromName: function (nomAuteur, idArticle, rang, idAuteur) {
                var data = {
                    "auteurNom": nomAuteur,
                    "idArticle": idArticle,
                    "rang": rang
                };
                var deferred = $q.defer();
                $http.post('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + nomAuteur + '/articleFromName', JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },

            // modifier la relation a ecrit à partir de l id de l'auteur
            modifyAEcritFromId: function (nomAuteur, idArticle, rang, idAuteur) {
                var data = {
                    "auteurNom": nomAuteur,
                    "idArticle": idArticle,
                    "rang": rang
                };
                var deferred = $q.defer();
                $http.put('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + idAuteur + '/article/' + idArticle, JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },

            // modifier la relation a ecrit à partir de l id de l'auteur
            modifyAEcritFromNom: function (nomAuteur, idArticle, rang) {
                var data = {
                    "auteurNom": nomAuteur,
                    "idArticle": idArticle,
                    "rang": rang
                };
                var deferred = $q.defer();
                $http.put('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + nomAuteur + '/articleFromName/' + idArticle + '/' + rang, JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },

            //supprimer tous les article à partir de l'id de l'auteur
            removeArticlesAuteurFromId: function (id) {
                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id + '/article').then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });
            },

            //supprimer tous les articles à partir du nom de l'auteur
            removeArticlesAuteurFromName: function (nom) {
                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + nom + '/articleFromName').then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });
            },

            //supprimer un article à partir de l'id de l'auteur
            removeArticleAuteurFromId: function (idArticle, idAuteur) {
                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + idAuteur + '/article/' + idArticle).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });
            },

            //supprimer un article à partir du nom de l'auteur
            removeArticleAuteurFromName: function (nom, id) {
                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + nom + '/articleFromName/' + id).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {

                    deferred.reject('Impossible de supprimer l\'article');
                    deferred.reject(response.data);
                });
            },

            //Obtention des universite d'un auteurs à partir de son id :
            getUniversity: function (id) {
                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id + '/university').then(function successCallback(response) {
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },

            //Obtention des universite d'un auteurs à partir de son nom :
            getUniversityFromName: function (nom) {
                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + nom + '/universityFromName').then(function successCallback(response) {
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;
            },

            //Création de la relation est rattaché : un auteur est rattache à une universite
            createEstRattacheUniversite: function (idAuteur, value) {
                var data = {
                    "value": value
                };
                var deferred = $q.defer();
                $http.post('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + idAuteur + '/university', JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;
            },

            //Mise à jour de la relation est rattaché : un auteur est rattache à une universite
            updateEstRattacheUniversite: function (idAuteur, nomAuteur, type, rank, value, idUniversity) {
                var data = {
                    "nomAuteur": nomAuteur,
                    "type": type,
                    "rank": rank,
                    "value": value
                };
                var deferred = $q.defer();
                $http.put('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + idAuteur + '/universityFromName/' + idUniversity + '/' + rank, JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },
            //Suppression de la relation est Rattache ou l'auteur est rattache à une universite
            deleteEstRattacheUniversite: function (idAuteur, rank, idUniversity) {

                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + idAuteur + '/universityFromName/' + idUniversity + '/' + rank).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },

            //Obtention de la liste des laboratoires auquel est rattache un auteur par l'id

            GetLaboratoiresAuteurFromId: function (id) {

                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + id + '/laboratory').then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;
            },
            //Obtention de la liste des laboratoires auquel est rattache un auteur par lenom

            GetLaboratoiresAuteurFromNom: function (nom) {

                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + nom + '/laboratoryFromName').then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;
            },

            //Création de la relation est rattaché : un auteur est rattache à un laboratoire
            createEstRattacheLaboratoire: function (idAuteur, value) {
                var data = {
                    "value": value
                };
                var deferred = $q.defer();
                $http.post('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + idAuteur + '/laboratory', JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },

            //Mise à jour de la relation est rattaché : un auteur est rattache à une universite
            updateEstRattacheLaboratoire: function (idAuteur, nomAuteur, type, rank, value, idLaboratoire) {
                var data = {
                    "nomAuteur": nomAuteur,
                    "type": type,
                    "rank": rank,
                    "value": value
                };
                var deferred = $q.defer();
                $http.put('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + idAuteur + '/laboratoryFromName/' + idLaboratoire + '/' + rank, JSON.stringify(data)).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },
            //Suppression de la relation est Rattache ou l'auteur est rattache à un laboratoire
            deleteEstRattacheLaboratoire: function (idAuteur, rank, idLaboratoire) {

                var deferred = $q.defer();
                $http.delete('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + idAuteur + '/laboratoryFromName/' + idLaboratoire + '/' + rank).then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;


            },
            //obtention des mots clés d'un auteur
            getKeywordAuteur: function (idAuteur) {
                var deferred = $q.defer();
                $http.get('http://localhost:8080/ServerOracleNoSQL/ws/auteur/' + idAuteur + '/keyword').then(function successCallback(response) {
                    $log.debug('... réponse du serveur : OK.');
                    deferred.resolve(response.data);
                }, function errorCallback(response) {
                    $log.debug('... réponse du serveur : erreur.');
                    deferred.reject('Impossible de créer l\'article');
                    deferred.reject(response.data);
                });
                return deferred.promise;
            }
        }
    }]);