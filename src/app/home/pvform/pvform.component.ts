import { Component, OnInit } from '@angular/core';
import { Form, FormArray, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { pvApplicationRequest } from '../../model/pvApplication-request.model';
import { PVAppService } from '../../service/pvApp.service';
import { DropdownData } from '../../model/dropdown-data-model';
import { ApplicationStorageService } from '../../service/application-storage.service';

@Component({
  selector: 'app-pvform',
  templateUrl: './pvform.component.html',
  styleUrl: './pvform.component.scss'
})
export class PvformComponent implements OnInit {



  pvForm!: FormGroup;
  firstPanel = true;
  panelOpenState = false;
  pvApplicationReq!: pvApplicationRequest;
  currencies!: DropdownData[];
  facilityTypes!: DropdownData[];
  documentTypes!: DropdownData[];
  fileForUpload!: Blob;
  formarray!: FormArray


  constructor(private pvAppService: PVAppService, private fb: FormBuilder, private applicationStorageService: ApplicationStorageService) {

  }

  ngOnInit(): void {
    this.pvAppService.getCurrencies().subscribe((res: any) => {
      this.currencies = res;
    });
    this.pvAppService.getFacilityTypes().subscribe((res: any) => {
      this.facilityTypes = res;
    });
    this.pvAppService.getDocumentTypes().subscribe((res: any) => {
      this.documentTypes = res;
    });
    console.log("business unit");
console.log(this.applicationStorageService.getBusinessUnit());
    this.pvForm = new FormGroup({
      username: new FormControl(this.applicationStorageService.getusername()),
      buisnessUnit: new FormControl(this.applicationStorageService.getBusinessUnit()),
      contactNumber: new FormControl(''),
      facilityType: new FormControl(''),
      catagory: new FormControl(''),
      purpose: new FormControl(''),
      term: new FormControl(''),
      ccy: new FormControl(''),
      amount: new FormControl(''),
      propertytype: new FormControl(''),
      fosreferenceNumber: new FormControl(''),
      borrowers: new FormArray([this.addBorrowerForm()]),
      comments: new FormArray([this.addCommentsForm()]),
      documents: new FormArray([this.addDocumentsForm()]),
    });
    this.applicationStorageService.getBusinessUnit();
    this.formarray = this.pvForm.get('borrowers') as FormArray;
  }

  addBorrowerForm(): FormGroup {
    return this.fb.group({
      name: "",
      customerNumber: "",
      address: "",
      contactNumber: "",
      email: ""
    });
  }

  addCommentsForm(): FormGroup {
    return this.fb.group({
      comment: "",
      createdDate: ""
    });
  }

  addDocumentsForm(): FormGroup {
    return this.fb.group({
      type: { id: "", name: "" },
      document: ""
    });
  }

  addBorrower() {
    this.formarray.push(this.addBorrowerForm())
  }

  deleteBorrower(i: number) {
    this.formarray.removeAt(i);
  }

  submitForm() {
    console.log(this.pvForm.value);
    this.pvApplicationReq = new pvApplicationRequest();
    this.populatePvApplicationRequest(this.pvApplicationReq);
    console.log(this.formarray);
    console.log(this.pvApplicationReq);
    this.pvAppService.createPvForm(this.pvApplicationReq, this.fileForUpload).subscribe(res =>{
      console.log(res);
    }, error =>{
      alert("Error occurs whilte creating application");
    })
  }

  populatePvApplicationRequest(request: pvApplicationRequest) {
    const formData = this.pvForm.value;
    request.fosreferenceNumber = formData.fosreferenceNumber;
    request.type = formData.propertytype;
    request.createBy = {
      userid: this.applicationStorageService.getUserId(),
      username: this.applicationStorageService.getusername(),
      businessUnit: this.applicationStorageService.getBusinessUnit(),
      contactNumber: formData.contactNumber,
      roles: { id: this.applicationStorageService.getroleId(), name: '' }
    };

    request.facilityDto = {
      amount: formData.amount,
      catagory: formData.catagory,
      purpose: formData.purpose,
      term: formData.term,
      ccy: { id: 0, name: formData.ccy },
      type: { id: 0, name: formData.facilityType }
    }
    request.borrowersDto = [...formData.borrowers];
    request.commentsDto = [...formData.comments];
    request.documentsDto = [
      {
        type: { id: 0, name: formData.type },
        document: ""
      }
    ];
   // request.documentsDto = [...formData.documents];

    // request.documentsDto[0] = new Document();
    // request.documentsDto[0].document = "file";
    // request.documentsDto[0].type = new DropdownData();
    // request.documentsDto[0].type.name = "sale deed";
  
  }

  onChangeFile(event: any) {
    if (event.target.files.length > 0) {
      console.log("file added");
      const file = event.target.files[0];
      console.log(file);
      this.fileForUpload = file;
    }
  }

}
