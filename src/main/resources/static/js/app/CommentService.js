'use strict';

angular.module('crudApp').factory('CommentService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllComments: loadAllComments,
                getAllComments: getAllComments,
                getComment: getComment,
                createComment: createComment,
                updateComment: updateComment,
                removeComment: removeComment
            };

            return factory;

            function loadAllComments() {
                console.log('Fetching all comments');
                var deferred = $q.defer();
                $http.get(urls.COMMENT_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all comments');
                            $localStorage.comments = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading comments');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllComments(){
                return $localStorage.comments;
            }

            function getComment(id) {
                console.log('Fetching Comment with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.COMMENT_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Comment with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading comment with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createComment(comment) {
                console.log('Creating Comment');
                var deferred = $q.defer();
                $http.post(urls.COMMENT_SERVICE_API, comment)
                    .then(
                        function (response) {
                            loadAllComments();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating Comment : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateComment(comment, id) {
                console.log('Updating Comment with id '+id);
                var deferred = $q.defer();
                $http.put(urls.COMMENT_SERVICE_API + id, comment)
                    .then(
                        function (response) {
                            loadAllComments();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Comment with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeComment(id) {
                console.log('Removing Comment with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.COMMENT_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllComments();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Comment with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);