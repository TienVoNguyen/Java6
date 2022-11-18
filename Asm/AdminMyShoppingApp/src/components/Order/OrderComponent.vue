<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="List Order" name="first" >
      <order-list @detailOrder="detailOrder"/>
    </el-tab-pane>
    <el-tab-pane label="OrderDetail" name="second">
      <edit-order :order="currentOrder" :orderDetail="currenOrderDetail"/>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import OrderList from "@/components/Order/OrderList";
import EditOrder from "@/components/Order/EditOrder";
import OrderDataService from "@/service/OrderDataService";
export default {
  data() {
    return {
      activeName: 'first',
      data: [],
      currentOrder:null,
      currenOrderDetail: null
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    detailOrder(event) {
      this.currentOrder = event
      OrderDataService.get(event.id).then(response => {
        this.currenOrderDetail = response.data
        console.log(this.currenOrderDetail)
      }).catch(error => {
        console.log(error)
      })
      this.activeName = 'second'
    }
  },
  components: {
    OrderList: OrderList,
    EditOrder: EditOrder
  }
}
</script>