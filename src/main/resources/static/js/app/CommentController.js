'use strict';

angular.module('crudApp').controller('CommentController',
    ['CommentService', '$scope',  function( CommentService, $scope) {

        var self = this;
        self.comment = {};
        self.comments=[];

        self.submit = submit;
        self.getAllComments = getAllComments;
        self.createComment = createComment;
        self.updateComment = updateComment;
        self.removeComment = removeComment;
        self.editComment = editComment;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
      
        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.comment.id === undefined || self.comment.id === null) {
                console.log('Saving New Comment', self.comment);
                createComment(self.comment);
            } else {
                updateComment(self.comment, self.comment.id);
                console.log('Comment updated with id ', self.comment.id);
            }
        }

        function createComment(comment) {
            console.log('About to create comment');
            CommentService.createComment(comment)
                .then(
                    function (response) {
                        console.log('Comment created successfully');
                        self.successMessage = 'Comment created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.comment={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Comment');
                        self.errorMessage = 'Error while creating Comment: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }

        function updateComment(comment, id){
            console.log('About to update comment');
            CommentService.updateComment(comment, id)
                .then(
                    function (response){
                        console.log('Comment updated successfully');
                        self.successMessage='Comment updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Comment');
                        self.errorMessage='Error while updating Comment '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeComment(id){
            console.log('About to remove Comment with id '+id);
            CommentService.removeComment(id)
                .then(
                    function(){
                        console.log('Comment '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing Comment '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllComments(){
            return CommentService.getAllComments();
        }
        
        function editComment(id) {
            self.successMessage='';
            self.errorMessage='';
            CommentService.getComment(id).then(
                function (comment) {
                    self.comment = comment;
                },
                function (errResponse) {
                    console.error('Error while removing comment ' + id + ', Error :' + errResponse.data);
                }
            );
        }
    }
]);