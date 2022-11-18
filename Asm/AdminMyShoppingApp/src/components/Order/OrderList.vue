<template>
  <el-container>
    <el-main>
      <el-table
          ref="multipleTable"
          :data="orderList.filter(data => !search || data.fullName.toLowerCase().includes(search.toLowerCase()))"
          @selection-change="handleSelectionChange"
          border
          style="width: 100%">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="fullName"
            label="Receiver"
            width="150">
        </el-table-column>
        <el-table-column
            prop="createBy"
            label="Create By"
            width="110">
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="Create Date"
            width="140">
        </el-table-column>
        <el-table-column
            prop="phoneNumber"
            label="Phone Number"
            width="140">
        </el-table-column>
        <el-table-column
            prop="address"
            label="Address">
        </el-table-column>
        <el-table-column
            label="Status"
            width="120">
          <template v-slot="scope">
            <span v-if="scope.row.status">Đã xác nhận</span>
            <span v-else>Chưa xác nhận</span>
          </template>
        </el-table-column>
        <el-table-column
            width="140"
            align="right">
          <template #header>
            <div class="header">
              <el-input
                  v-model="search"
                  size="medium"
                  placeholder="Type to search"/>
            </div>
          </template>
          <template v-slot="scope">
            <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)">Edit
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer>
      <el-pagination
          background
          layout="prev, pager, next"
          :total="itemCount"
          :page-count="count"
          :page-size="pageSize"
          :page-sizes="pageSizes"
          @current-change="handlePageChange"
          hide-on-single-page>
      </el-pagination>
    </el-footer>
  </el-container>
</template>

<script>
import OrderDataService from "@/service/OrderDataService";

export default {
  name: "OrdersComponent",
  data() {
    return {
      orderList: [],
      currentIndex: -1,
      page: 1,
      count: 0,
      pageSize: 5,
      itemCount: 6,
      pageSizes: [5, 10, 15],
      multipleSelection: [],
      search: ''
    }
  },
  methods: {
    retrieveProductList() {
      const params = this.getRequestParams(
          this.page,
          this.pageSize
      );
      OrderDataService.getAll(params)
          .then(response => {
            console.log(response.data.orders)
            this.orderList = response.data.orders;
            this.count = response.data.totalPages;
            this.itemCount = response.data.totalItems;
          })
          .catch(e => {
            console.log(e);
          });

    },

    refreshList() {
      this.retrieveProductList();
      this.currentIndex = -1;
    },

    setActiveProduct(product, index) {
      this.currentProduct = product;
      this.currentIndex = index;
    },

    getRequestParams(page, pageSize) {
      let params = {};

      if (page) {
        params["page"] = page - 1;
      }

      if (pageSize) {
        params["size"] = pageSize;
      }

      return params;
    },

    // searchTitle() {
    //   ProductDataService.findByTitle(this.title)
    //       .then(response => {
    //         this.productList = response.data;
    //         console.log(response.data);
    //       })
    //       .catch(e => {
    //         console.log(e);
    //       });
    // },
    handleEdit(index, row) {
      console.log(index, row)
      this.$emit('detailOrder', row)
    },

    handlePageChange(value) {
      this.page = value
      console.log(this.page)
      this.retrieveProductList()
    },

    handleSelectionChange(val) {
      let ids = []
      val.forEach(v => {
        ids.push(v.id)
      })
      this.multipleSelection = [...ids]
      console.log(ids)
      console.log(this.multipleSelection)
    },

    clearSelection() {
      this.$refs.multipleTable.clearSelection()
    }
  },

  mounted() {
    this.retrieveProductList();
  }
}
</script>