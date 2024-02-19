import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { pvApplicationRequest } from '../../model/pvApplication-request.model';
import { PVAppService } from '../../service/pvApp-service';
import { DropdownData } from '../../model/dropdown-data-model';
import { Borrower } from '../../model/borrower-model';
import { User } from '../../model/user-model';
import { Facilty } from '../../model/facility-model';
import { Comment } from '../../model/comment-model';
import { Document } from '../../model/document-model';

@Component({
  selector: 'app-pvform',
  templateUrl: './pvform.component.html',
  styleUrl: './pvform.component.scss'
})
export class PvformComponent implements OnInit{
  
  pvForm!: FormGroup;
  firstPanel = true;
  panelOpenState = false;
  pvApplicationReq!: pvApplicationRequest;
  currencies!:DropdownData[];
  facilityTypes!: DropdownData[];
  documentTypes!: DropdownData[];
  fileForUpload!: Blob;


  constructor(private pvAppService: PVAppService){

  }

  ngOnInit(): void {
    this.pvForm = new FormGroup({
      username: new FormControl(''),
      buisnessUnit: new FormControl(''),
      contactNumber: new FormControl(''),
      facilityType: new FormControl(''),
      catagory: new FormControl(''),
      purpose: new FormControl(''),
      term: new FormControl(''),
      ccy: new FormControl(''),
      amount: new FormControl(''),
      propertytype: new FormControl(''),
      fosreferenceNumber: new FormControl(''),
      customerName: new FormControl(''),
      customerNumber: new FormControl(''),
      address: new FormControl(''),
      borrowercontactNumber: new FormControl(''),
      email: new FormControl(''),
      comment: new FormControl('')
    });
    this.pvAppService.getCurrencies().subscribe((res: any) => {
      console.log(res);
      this.currencies = res;
    });
    this.pvAppService.getFacilityTypes().subscribe((res: any) => {
      console.log(res);
      this.facilityTypes = res;
    });
    this.pvAppService.getDocumentTypes().subscribe((res: any) => {
      console.log(res);
      this.documentTypes = res;
    });

  }

  submitForm() {
    //console.log(this.pvForm.value);
    this.pvApplicationReq = new pvApplicationRequest();
    this.populatePvApplicationRequest(this.pvApplicationReq);
    console.log(this.pvApplicationReq);
    this.pvAppService.createPvForm(this.pvApplicationReq, this.fileForUpload).subscribe(res =>{
      console.log(res);
    }, error =>{
      alert("Error occurs whilte creating application");
    })
  }

  populatePvApplicationRequest(request: pvApplicationRequest){
    request.fosreferenceNumber = this.pvForm.value.fosreferenceNumber;
    request.type = this.pvForm.value.propertytype;
    request.createBy = new User();
    request.createBy.username = this.pvForm.value.username;
    request.createBy.buisnessUnit = this.pvForm.value.buisnessUnit;
    request.createBy.contactNumber =  this.pvForm.value.contactNumber;
    request.facilityDto = new Facilty();
    request.facilityDto.amount =  this.pvForm.value.amount;
    request.facilityDto.catagory =  this.pvForm.value.catagory;
    request.facilityDto.purpose =  this.pvForm.value.purpose;
    request.facilityDto.term = this.pvForm.value.term;
    request.facilityDto.ccy = new DropdownData();
    request.facilityDto.type = new DropdownData();
    request.facilityDto.ccy.name =  this.pvForm.value.ccy;
    request.facilityDto.type.name =  this.pvForm.value.facilityType;
    request.borrowersDto[0] = new Borrower();
    request.borrowersDto[0].name = this.pvForm.value.customerName;
    request.borrowersDto[0].address = this.pvForm.value.address;
    request.borrowersDto[0].contactNumber = this.pvForm.value.borrowercontactNumber;
    request.borrowersDto[0].customerNumber = this.pvForm.value.customerNumber;
    request.borrowersDto[0].email =  this.pvForm.value.email;
    
    request.commentsDto[0] = new Comment();
    request.commentsDto[0].comments = this.pvForm.value.comment;
    request.commentsDto[0].createdDate =  "12/1/2014";

    request.documentsDto[0] = new Document();
    request.documentsDto[0].document = "file";
    request.documentsDto[0].type = new DropdownData();
    request.documentsDto[0].type.name = "sale deed";
  }

  onChangeFile(event:any){
    if(event.target.files.length > 0){
      console.log("file added");
        const file = event.target.files[0];
        console.log(file);
        this.fileForUpload = file;  
    }
  }

}
