angular.module('sms-server').controller('StatisticsController', ['$scope', '$http', 'RestURLFactory',
    function ($scope, $http, RestURLFactory) {
        var sortConstants = {
            notSorted: {
                elementClass: "fa fa-sort"
            },
            asc: {
                elementClass: "fa fa-sort-asc",
                    sortString: 'asc'
            },
            desc: {
                elementClass: "fa fa-sort-desc",
                    sortString: 'desc'
            }
        };

        var sortOrder = {
            error: sortConstants.notSorted,
                smsType: sortConstants.notSorted,
                username: sortConstants.notSorted,
                number: sortConstants.notSorted,
                recipientType: sortConstants.notSorted,
                sentDate: sortConstants.desc
        };

        var sortField = 'sentDate';
        var sortDirection = sortConstants.desc.sortString;

        var sortBy = function (field) {
            if (sortOrder[field] === sortConstants.notSorted) {
                sortOrder[field] = sortConstants.asc;
            } else if (sortOrder[field] === sortConstants.asc) {
                sortOrder[field] = sortConstants.desc;
            } else if (sortOrder[field] === sortConstants.desc) {
                sortOrder[field] = sortConstants.asc;
            }

            sortDirection = sortOrder[field].sortString;
            sortField = field;

            for (var sortOrderField in sortOrder) {
                if (sortOrderField != field) {
                    sortOrder[sortOrderField] = sortConstants.notSorted;
                }
            }
        };

        $scope.sortConstants = sortConstants;
        $scope.sortOrder = sortOrder;

        $scope.sortBy = function (field) {
            sortBy(field);
            $scope.loadStatistics();
        };

        var pageSize = {
            ten: {
                display: "10",
                    value: 10,
                    active: true
            },
            fifteen: {
                display: "15",
                    value: 15,
                    active: false
            },
            fifty: {
                display: "50",
                    value: 50,
                    active: false
            }
        };
        
        var currentPage = 1;
        var currentPageSize = pageSize.ten;
        var pages = [];
        var skip = 0;
        var count = 0;

        var recalculatePages = function () {
            pages = [];
            for (var i = 0; i < count; i++) {
                var page = {
                    pageNumber: i + 1,
                    active: currentPage - 1 === i
                };
                pages.push(page);
                $scope.pages = pages;
            }
        };

        var changePage = function (targetPage) {
            skip = (targetPage - 1) * currentPageSize.value;
            
            currentPage = targetPage;
            recalculatePages();
        };

        var changePageSize = function (targetPageSize) {
            currentPageSize.active = false;
            currentPageSize = targetPageSize;
            currentPageSize.active = true;
        };
        
        $scope.pageSize = pageSize;
        $scope.pages = pages;
        
        $scope.getPage = function (targetPage) {
            changePage(targetPage);
            $scope.loadStatistics();
        };
        
        $scope.changePageSize = function (targetPageSize) {
            console.log(targetPageSize);
            changePageSize(targetPageSize);
            $scope.loadStatistics();
        };

        $scope.statistics = [];
        
        $scope.loadStatistics = function () {
            $http.get(RestURLFactory.GET_FULL_STATISTICS + 
                '/page?skip=' + skip + '&offset=' + currentPageSize.value + '&sortField=' + sortField + '&sortDirection=' + sortDirection)
                .success(function (data) {
                    $scope.statistics = data;
                });
            $http.get(RestURLFactory.GET_FULL_STATISTICS +
                    '/count')
                .success(function (data) {
                    count = Math.ceil(data / currentPageSize.value);
                    recalculatePages();
                });
        };
    }
]);