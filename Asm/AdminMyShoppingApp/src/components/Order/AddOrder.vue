<template>
    <el-main>
      <el-header v-if="message"
                 class="alert text-center"
                 :class="successful ? 'alert-success' : 'alert-danger'">
        {{ message }}
      </el-header>
      <el-form :model="product" id="form-product" :rules="rules" ref="product" label-width="120px" class="demo-product">
        <el-form-item label="Product name" prop="name">
          <el-input type="text" name="name" v-model.trim="product.name"></el-input>
        </el-form-item>
        <el-form-item label="Category" prop="categoryId">
          <b-form-select :state="Boolean(product.categoryId)" name="categoryId" v-model="product.categoryId"
                         class="mb-3">
            <template #first>
              <b-form-select-option value="" disabled>-- Please select an option --</b-form-select-option>
            </template>
            <b-form-select-option v-for="cate in categories" :key="cate.id" :value="cate.id">{{ cate.name }}
            </b-form-select-option>
          </b-form-select>

        </el-form-item>
        <el-form-item label="Product price" prop="price">
          <el-input name="price" v-model.trim="product.price"></el-input>
        </el-form-item>
        <el-form-item label="Product quantity" prop="quantity">
          <el-input name="quantity" v-model.trim="product.quantity"></el-input>
        </el-form-item>
        <el-form-item label="Description" prop="description">
          <el-input name="description" type="textarea" v-model.trim="product.description"></el-input>
        </el-form-item>
        <el-form-item label="Product image" prop="image">
          <b-form-file
              v-model="product.image"
              name="image" accept="image/*"
              placeholder="Choose your images..."
          >
            <template #file-name="{ names }">
              <b-badge variant="dark">{{ names[0] }}</b-badge>
              <b-badge v-if="names.length > 1" variant="dark" class="ml-1">
                + {{ names.length - 1 }} More images
              </b-badge>
            </template>
          </b-form-file>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('product')">Create</el-button>
          <el-button @click="resetForm('product')">Reset</el-button>
        </el-form-item>
      </el-form>
    </el-main>
</template>

<script>
import ProductDataService from "@/service/ProductDataService";
import CategoryDataService from "@/service/CategoryDataService";

export default {
  name: "AddProduct",

  data() {
    return {
      product: {
        name: '',
        description: '',
        quantity: '',
        categoryId: '',
        price: '',
        image: []
      },
      message: '',
      successful: false,
      categories: [],
      rules: {
        name: [
          {required: true, message: 'Please input name product', trigger: 'blur'},
          {min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur'}
        ],
        categoryId: [
          {required: true, message: 'Please select Category ', trigger: 'change'}
        ],
        description: [
          {required: true, message: 'Please input description product', trigger: 'blur'}
        ],
        image: [
          {required: true, message: 'Please input image product', trigger: 'change'}
        ]
      }
    };
  },
  methods: {
    vali(formName) {
      this.$refs[formName].validate((valid) => {
        console.log(valid)
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let form = document.querySelector('#form-product');
          console.log(form);
          ProductDataService.create(form).then(() => {
            this.message = "Successfully"
            this.successful = true;
          }).catch(() => {
            this.message = "Has an error"
            this.successful = false;
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    // this.product = this.currentProduct
    CategoryDataService.getAll()
        .then(response => {
          this.categories = response.data
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
  }
}
</script>

<style scoped>

</style>