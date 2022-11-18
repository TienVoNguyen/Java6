import http from "../commons/http-common";
import {jsonRequest} from "@/commons/http-common";
import authHeader from "@/service/auth-header";
class ProductDataService {
    getAll(params) {
        return http.get("product", {params, headers: authHeader()});
    }

    get(id) {
        return http.get(`/todo/${id}`);
    }

    create(data) {
        let fromData = new FormData(data);
        console.log(fromData);
        return http.post("product/create", fromData,{headers: authHeader()});
    }

    update(id, data) {
        let fromData = new FormData(data);
        return http.put(`product/update/${id}`, fromData,{headers: authHeader()});
    }

    deleteAll(data) {
        return jsonRequest.delete("product/delete", {headers: authHeader(data), data: data});
    }

    delete(id) {
        return http.delete(`/product/delete/${id}`);
    }

    findByTitle(title) {
        return http.get(`/todo?title=${title}`);
    }
}

export default new ProductDataService();