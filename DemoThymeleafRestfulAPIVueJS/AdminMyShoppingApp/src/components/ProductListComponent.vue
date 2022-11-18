<template>
  <el-container>
    <el-header>
      <el-select v-model="categoryCode" @change="findByCategoryCode()" placeholder="Select Category">
        <el-option value="" label="Select Category"></el-option>
        <el-option
            v-for="item in categories"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
    </el-header>
    <el-main>
      <el-table
          ref="multipleTable"
          :data="this.categoryCode.length<=0?productList.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase())):this.productListCode"
          @selection-change="handleSelectionChange"
          border
          style="width: 100%">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="name"
            label="Name"
            width="150">
        </el-table-column>
        <el-table-column
            prop="description"
            label="Description"
            width="180">
        </el-table-column>
        <el-table-column
            prop="price"
            label="Price"
            width="140">
        </el-table-column>
        <el-table-column
            label="Category">
          <template v-slot="scope">
            <div v-for="cate in categories" :key="cate.id">
              <span v-show="scope.row.categoryId === cate.id">
                {{ cate.name }}
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
            label="Cover">
          <template v-slot="scope">
            <img height="50px"
                 :src="'http://localhost:1997/api/files/images?file=' + scope.row.image" alt="anh">
          </template>
        </el-table-column>
        <el-table-column
            label="Available"
            width="100">
          <template v-slot="scope">
            <span v-if="scope.row.available">Đang bán</span>
            <span v-else>Tạm ngừng</span>
          </template>
        </el-table-column>
        <el-table-column
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
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 20px">
        <el-button type="danger" @click="removeProductList">Delete</el-button>
        <el-button type="primary" @click="handleAdd()">Add Product</el-button>
        <el-button @click="clearSelection">Clear selection</el-button>
      </div>
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
import ProductDataService from "@/service/ProductDataService";
import CategoryDataService from "@/service/CategoryDataService";

export default {
  name: "ProductListComponent",
  data() {
    return {
      productList: [],
      productListCode: [],
      categories: [],
      currentProduct: null,
      currentIndex: -1,
      page: 1,
      count: 0,
      pageSize: 5,
      itemCount: 6,
      pageSizes: [5, 10, 15],
      multipleSelection: [],
      search: '',
      categoryCode: ''
    }
  },
  methods: {
    retrieveProductList() {
      const params = this.getRequestParams(
          this.page,
          this.pageSize
      );
      console.log(params)
      // let data = JSON.stringify(params)
      ProductDataService.getAll(params)
          .then(response => {
            console.log(response.data)
            this.productList = response.data.productDtoList;
            this.count = response.data.totalPages;
            this.itemCount = response.data.totalItems;
          })
          .catch(e => {
            console.log(e);
          });
    },

    refreshList() {
      this.retrieveProductList();
      this.currentProduct = null;
      this.currentIndex = -1;
    },

    setActiveProduct(product, index) {
      this.currentProduct = product;
      this.currentIndex = index;
    },

    removeProductList() {
      ProductDataService.deleteAll(this.multipleSelection)
          .then(() => {
            this.refreshList();
          })
          .catch(e => {
            console.log(e);
          });
    },

    getRequestParams(page, pageSize) {
      let params = {};

      // if (searchTitle) {
      //   params["title"] = searchTitle;
      // }

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
    findByCategoryCode() {
      console.log(this.categoryCode)
      this.productListCode = this.productList.filter((product) => {
        console.log(product.categoryId)
        return this.categoryCode === product.categoryId
      })
    },

    handleEdit(index, row) {
      console.log(index, row)
      this.$emit('detailProduct', row)
    },

    handleAdd() {
      this.$emit('detailProduct')
    },

    handleDelete(index, row) {
      console.log(index, row)
      ProductDataService.delete(row.id).then(()=>{
        console.log("thanh cong")
        this.refreshList();
      }).catch((error)=>{
        console.log(error)
      } )
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

  created() {
    CategoryDataService.getAll()
        .then(response => {
          this.categories = response.data
        })
        .catch(e => {
          console.log(e);
        });
  },

  mounted() {
    this.retrieveProductList();
  }
}
</script>