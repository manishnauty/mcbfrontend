import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ApiConstants, AppConstants } from "../app.constants";
import { PVApplicationRequest } from "../model/pvApplication-request.model";

@Injectable({
    providedIn: 'root'
})
export class PVAppService {
    constructor(private http: HttpClient) {
    }

    getCurrencies(){
        let url = AppConstants.HOST + ApiConstants.PV_APP_CURRENCY_URL;
        return this.http.get<any>(url);
    }
    getFacilityTypes(){
        let url = AppConstants.HOST + ApiConstants.PV_APP_FACILITY_TYPE_URL;
        return this.http.get<any>(url);
    }
    getDocumentTypes(){
        let url = AppConstants.HOST + ApiConstants.PV_APP_DOCUMENTTYPE_URL;
        return this.http.get<any>(url);
    }
    getNextAppRefNo(){
        let url = AppConstants.HOST + ApiConstants.PV_APP_NEXT_REF_NO;
        return this.http.get<any>(url);
    }
    createPvForm(pvApplicationReq: PVApplicationRequest, file: Blob) {
        let url = AppConstants.HOST + ApiConstants.PV_APP_CREATE_FORM_URL;
        let formData = new FormData();
        formData.append("file",file);
        formData.append("appData",JSON.stringify(pvApplicationReq));

        return this.http.post<any>(url, formData);
    }
    fetchApplications(){
        let url = AppConstants.HOST + ApiConstants.PV_APP_FETCH_APPLICATIONS;
        return this.http.get<any>(url);
    }
}