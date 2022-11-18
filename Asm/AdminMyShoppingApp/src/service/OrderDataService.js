import http, {jsonRequest} from "../commons/http-common";
import authHeader from "@/service/auth-header";

class ProductDataService {
    getAll(params) {
        return http.get("order", {params, headers: authHeader()});
    }

    get(id) {
        return http.get(`order/detail/${id}`, { headers: authHeader()});
    }

    create(data) {
        let fromData = new FormData(data);
        console.log(fromData);
        return http.post("order/create", fromData,{headers: authHeader()});
    }

    update(id, data) {
        console.log(data)
        return jsonRequest.put(`order/update/${id}`, data,{headers: authHeader() });
    }


    findByTitle(title) {
        return http.get(`/order?title=${title}`);
    }
}

export default new ProductDataService();