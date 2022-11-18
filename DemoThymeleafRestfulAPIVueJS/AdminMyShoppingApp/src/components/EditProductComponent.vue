<template>
  <div>
    <el-header v-if="message"
               class="alert text-center"
               :class="successful ? 'alert-success' : 'alert-danger'">
      {{ message }}
    </el-header>
    <form class="was-validated" id="editProduct">
      <div class="form-group">
        <label for="category">Select Category:</label>
        <select class="form-control" id="category" name="categoryId">
          <option v-for="cate in categories" :key="cate.id" :value="cate.id" :selected="cate.id === product.categoryId">{{ cate.name }}</option>
        </select>
      </div>
      <div class="form-group">
        <label for="name">Product Name:</label>
        <input type="text" class="form-control" id="name" :value="product.name" placeholder="Enter product name"
               name="name" required>
        <div class="valid-feedback">Valid.</div>
        <div class="invalid-feedback">Please fill out this field.</div>
      </div>
      <div class="form-group">
        <label for="price">Product Price:</label>
        <input type="text" class="form-control" id="price" :value="product.price" placeholder="Enter product name"
               name="price" required>
        <div class="valid-feedback">Valid.</div>
        <div class="invalid-feedback">Please fill out this field.</div>
      </div>
      <div class="form-group">
        <label for="quantity">Product Price:</label>
        <input type="text" class="form-control" id="quantity" :value="product.quantity" placeholder="Enter product name"
               name="quantity" required>
        <div class="valid-feedback">Valid.</div>
        <div class="invalid-feedback">Please fill out this field.</div>
      </div>
      <label>Product available:</label>
      <div class="form-group">
        <div class="form-check-inline">
          <label class="form-check-label">
            <input type="radio" class="form-check-input" :checked="product.available" value="true" name="available">Bán
          </label>
        </div>
        <div class="form-check-inline">
          <label class="form-check-label">
            <input type="radio" class="form-check-input" :checked="!product.available" value="false" name="available">Ngưng bán
          </label>
        </div>
      </div>
      <div class="form-group">
        <label for="image">Product image:</label>
        <input type="file" class="form-control-file border" name="image" id="image">
      </div>
      <div class="form-group">
        <label for="description">Product Description:</label>
        <textarea class="form-control" rows="5" id="description" name="description"
                  v-bind:value="product.description"></textarea>
      </div>
      <button type="button" @click.prevent="editProduct" class="btn btn-primary">Update</button>
    </form>
  </div>
</template>

<script>
import CategoryDataService from "@/service/CategoryDataService";
import ProductDataService from "@/service/ProductDataService";

export default {

  name: "EditProductComponent",
  props: {
    product: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      categories: [],
      message: '',
      successful: false
    }
  },
  created() {
    CategoryDataService.getAll()
        .then(response => {
          this.categories = response.data
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
  },
  methods: {
    editProduct() {
      let form = document.querySelector('#editProduct');
      console.log(form);
      ProductDataService.update(this.product.id, form).then(() => {
        this.message = "Successfully"
        this.successful = true;
        this.$emit('loadProduct')
      }).catch(() => {
        this.message = "Has an error"
        this.successful = false;
      })
    }
  }
}
</script>

<style scoped>

</style>