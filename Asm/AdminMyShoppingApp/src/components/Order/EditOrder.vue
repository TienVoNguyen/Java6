<template>
  <el-main>
    <el-main>
      <el-alert v-if="order == null"
          title="Please select an Order"
          type="error"
          center
          show-icon :closable="false">
      </el-alert>
      <div v-else>
      <el-form label-position="top" class="demo-form-inline" size="medium">
        <el-col :span="11">
          <el-form-item label="Receiver">
            <el-input readonly :value="order.fullName"></el-input>
          </el-form-item>
        </el-col>
        <el-col class="line" :span="2"><span>&nbsp;</span></el-col>
        <el-col :span="11">
          <el-form-item label="Create By">
            <el-input readonly :value="order.createBy"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="Phone Number">
            <el-input readonly :value="order.phoneNumber"></el-input>
          </el-form-item>
        </el-col>
        <el-col class="line" :span="2"><span>&nbsp;</span></el-col>
        <el-col :span="11">
          <el-form-item label="Create Date">
            <el-input readonly :value="order.createDate"></el-input>
          </el-form-item>
        </el-col>
        <el-form-item label="Create Date">
          <el-input type="textarea" readonly :value="order.address"></el-input>
        </el-form-item>
      </el-form>
        <el-table
            ref="multipleTable"
            :data="orderDetail"
            border
            style="width: 100%">
          <el-table-column
              label="Image"
              width="260">
            <template v-slot="scope">
              <img height="80px" :src="'http://localhost:1997/images/'+scope.row.productImage" alt="Product Image">
            </template>
          </el-table-column>
          <el-table-column
              prop="productName"
              label="Product Name"
              width="220">
          </el-table-column>
          <el-table-column
              prop="productDescription"
              label="Product Description"
              width="300">
          </el-table-column>
          <el-table-column
              prop="price"
              label="Price"
              width="120">
          </el-table-column>
          <el-table-column
              prop="quantity"
              label="Quantity"
              width="120">
          </el-table-column>
          <el-table-column
              label="Total"
              width="120">
            <template v-slot="scope">
              <span>{{ scope.row.price * scope.row.quantity }}</span>
            </template>
          </el-table-column>
        </el-table>
        <br>
        <el-row class="text-center" v-show="!order.status">
          <el-button @click="updateOrder(order)" type="primary" round>Confirm</el-button>
        </el-row>
      </div>
    </el-main>

  </el-main>
</template>

<script>
import OrderDataService from "@/service/OrderDataService";

export default {
  name: "EditOrderComponent",
  props: {
    order: {
      type: Object,
      required: false
    },
    orderDetail: {
      type: Array,
      required: false
    }
  },
  data() {
    return {
      categories: [],
      successful: false,
      status: false
    }
  },
  methods: {
    updateOrder(order) {
      OrderDataService.update(this.order.id, this.order).then( () => {
        this.$message({
          message: 'Congrats, this is a success message.',
          type: 'success'
        });
        order.status = true
      }).catch(() => {
        this.$message({
          message: 'Warning, this is a warning message.',
          type: 'warning'
        });
      })
    }
  }
}
</script>

<style scoped>

</style>