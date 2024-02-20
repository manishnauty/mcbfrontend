import { Component, OnInit } from '@angular/core';
import { Form, FormArray, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { PVApplicationRequest } from '../../model/pvApplication-request.model';
import { PVAppService } from '../../service/pvApp.service';
import { DropdownData } from '../../model/dropdown-data-model';
import { ApplicationStorageService } from '../../service/application-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pvform',
  templateUrl: './pvform.component.html',
  styleUrl: './pvform.component.scss'
})
export class PvformComponent implements OnInit {



  pvForm!: FormGroup;
  firstPanel = true;
  panelOpenState = false;
  pvApplicationReq!: PVApplicationRequest;
  currencies!: DropdownData[];
  facilityTypes!: DropdownData[];
  documentTypes!: DropdownData[];
  nexRefNo!: number;
  fileForUpload!: Blob;
  formarray!: FormArray;
  comments!: FormArray;
  documents!: FormArray


  constructor(private pvAppService: PVAppService, private fb: FormBuilder, private applicationStorageService: ApplicationStorageService,
    private router: Router) {
  }

  ngOnInit(): void {


    this.pvAppService.getCurrencies().subscribe((res: any) => {
      this.currencies = res;
    }, (error => {
      alert("Error occured while fetching currencies");
    }
    ));
    this.pvAppService.getFacilityTypes().subscribe((res: any) => {
      this.facilityTypes = res;
    }, (error => {
      alert("Error occured while fetching facilities");
    }
    ));
    this.pvAppService.getDocumentTypes().subscribe((res: any) => {
      this.documentTypes = res;
    }, (error => {
      alert("Error occured while fetching Document Types");
    }
    ));

    let month = (new Date()).getMonth() + 1;
    const referenceNumber = (new Date()).getFullYear() + "/" + month + "/" + this.applicationStorageService.getRefNo();

    this.pvForm = new FormGroup({
      username: new FormControl({ value: this.applicationStorageService.getusername(), disabled: true }),
      buisnessUnit: new FormControl({ value: this.applicationStorageService.getBusinessUnit(), disabled: true }),
      contactNumber: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern("^[0-9]*$")
      ])),
      facilityType: new FormControl('', Validators.required),
      catagory: new FormControl('', Validators.required),
      purpose: new FormControl('', Validators.required),
      term: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern("^[0-9]*$")
      ])),
      ccy: new FormControl('', Validators.required),
      amount: new FormControl('', Validators.compose(
        [Validators.required,
        Validators.pattern("^[0-9]*$")
        ]
      )),
      propertytype: new FormControl('', Validators.required),
      fosreferenceNumber: new FormControl(referenceNumber),
      borrowers: new FormArray([this.addBorrowerForm()]),
      comments: new FormArray([this.addCommentsForm()]),
      documents: new FormArray([this.addDocumentsForm()]),
    });
    this.formarray = this.pvForm.get('borrowers') as FormArray;
    this.documents = this.pvForm.get('documents') as FormArray;
    this.comments = this.pvForm.get('comments') as FormArray;
  }

  addBorrowerForm(): FormGroup {
    return this.fb.group({
      name: ["", Validators.compose(
        [Validators.required
        ]
      )],
      customerNumber: ["", Validators.required],
      address: ["", Validators.required],
      contactNumber: ["", Validators.compose(
        [Validators.required,
        Validators.pattern("^[0-9]*$")
        ]
      )],
      email: ["", Validators.compose(
        [Validators.required,
        Validators.email,
        ]
      )],
    });
  }

  addCommentsForm(): FormGroup {
    return this.fb.group({
      comment: ["", Validators.required],
      createdDate: ""
    });
  }

  addDocumentsForm(): FormGroup {
    return this.fb.group({
      type: ["", Validators.required],
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
    this.pvApplicationReq = new PVApplicationRequest();
    this.populatePVApplicationRequest(this.pvApplicationReq);
    this.pvAppService.createPvForm(this.pvApplicationReq, this.fileForUpload).subscribe(res => {
      alert("New application created Successfully");
      this.router.navigate(['home/pvview']);
    }, (error => {
      //alert("Error occured while creating application");
    }
    ));
  }

  populatePVApplicationRequest(request: PVApplicationRequest) {
    const formData = this.pvForm.value;
    request.fosreferenceNumber = formData.fosreferenceNumber;
    request.type = formData.propertytype;
    request.createdBy = {
      userid: this.applicationStorageService.getUserId(),
      username: this.applicationStorageService.getusername(),
      businessUnit: this.applicationStorageService.getBusinessUnit(),
      contactNumber: formData.contactNumber,
      roles: []
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
        type: { id: 0, name: formData.documents[0].type },
        document: ""
      }
    ];
  }

  onChangeFile(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.fileForUpload = file;
    }
  }

  viewAppPage() {
    this.router.navigate(['home/pvview']);
  }

}
