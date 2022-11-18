<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="List Product" name="first">
      <product-list-component @detailProduct="detailProduct"></product-list-component>
    </el-tab-pane>
    <el-tab-pane :label="labelProduct" name="second">
        <add-product v-if="currentProduct == null"></add-product>
        <edit-product-component v-else :product="currentProduct"></edit-product-component>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import AddProduct from "@/components/AddProduct";
import ProductListComponent from "@/components/ProductListComponent";
import EditProductComponent from "@/components/EditProductComponent";
export default {
  data() {
    return {
      activeName: 'first',
      currentProduct: null,
      labelProduct: 'Add Product'
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    detailProduct(event) {
      if(event) {
        this.labelProduct = 'Update Product'
        this.currentProduct = event
      }else {
        this.labelProduct = 'Add Product'
        this.currentProduct = null
      }
      console.log(event)
      this.activeName= 'second'
    }
  },
  components: {
    addProduct: AddProduct,
    ProductListComponent: ProductListComponent,
    EditProductComponent: EditProductComponent
  }
}
</script>