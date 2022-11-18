var app = new Vue({
    el: '#app',
    data: {
        items: [],
        quantity: 1,
        orderDetail: {},
        address: '',
        phoneNumber: ''
    },
    created() {
        let items = localStorage.getItem("cart");
        if(items !== null) this.items = JSON.parse(items);
    },
    computed: {
        getCount() {
            return this.items
                .map(item => item.quantity)
                .reduce((total, quantity) => total += quantity, 0);

        },
        getAmount() {
            return this.items
                .map(item => item.quantity * item.price)
                .reduce((total, qty) => total += qty, 0);
        }
    },
    methods: {
        add: function (id) {
            let pid = id.path[0].getAttribute("data-id");
            console.log(this.quantity)
            let item = this.items.find(item => item.id == pid);
            const url = "http://localhost:1997/api/product/" + pid;
            if (item) {
                if (this.quantity > 1) {
                    item.quantity =parseInt(item.quantity)  + parseInt(this.quantity);
                }else {
                    item.quantity++;
                }
                this.saveToLocalStorage();
            } else {
                this.$http.get(url).then(resp => {
                    console.log(resp);
                    resp.data.quantity = this.quantity;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                })
            }
        },
        removeItem(index) {
            this.items.splice(index, 1);
            this.saveToLocalStorage();
        },
        removeAll() {
            this.items = [];
            this.saveToLocalStorage();
        },
        saveToLocalStorage() {
            let json = JSON.stringify(this.items);
            localStorage.setItem("cart", json);
        },
        purchase() {
            let orderDetail = this.items.map(item => {
                    return {
                        product: {id: item.id},
                        price: item.price,
                        quantity: item.quantity
                    }
                });
            let fullName = document.querySelector('#fullName').value
            let order = {
                address: this.address,
                phoneNumber: this.phoneNumber,
                fullName: fullName,
                orderDetail: orderDetail
            }
            const url = "http://localhost:1997/api/order";
            this.$http.post(url, order).then(resp => {
                console.log(resp);
                this.removeAll();
                location.href = "http://localhost:1997/order/detail/" + resp.data.id
            }).catch(error => {
                console.log("Loi");
                console.log(error);
            })
        }
    },
    filters: {
        dateFormat(date){
            return new Intl.DateTimeFormat('en-US', { dateStyle: 'long' }).format(new Date(date));

        }
    }
})