import { Component, OnInit } from '@angular/core';
import { PVAppService } from '../../service/pvApp.service';
import { Router } from '@angular/router';
import { ApplicationStorageService } from '../../service/application-storage.service';

export interface PeriodicElement {
  reference: string;
  type: string,
  borrowerName: string;
  fosRef: string;
  createdOn: string;
  modifiedOn: string;
}

const SAMPLE_TABLE_DATA: PeriodicElement[] = [
  { reference: 'PV20230912', type: 'New', borrowerName: 'customer one', fosRef: '12092390', createdOn: '12/12/2012 09:24', modifiedOn: 'user' },
  { reference: 'PV20230913', type: 'New', borrowerName: 'customer two', fosRef: '12092390', createdOn: '12/12/2012 09:24', modifiedOn: 'user' },
  { reference: 'PV20230914', type: 'New', borrowerName: 'customer three', fosRef: '12092390', createdOn: '12/12/2012 09:24', modifiedOn: 'user' },
  { reference: 'PV20230915', type: 'New', borrowerName: 'customer four', fosRef: '12092390', createdOn: '12/12/2012 09:24', modifiedOn: 'user' },
  { reference: 'PV20230918', type: 'New', borrowerName: 'customer one', fosRef: '12092390', createdOn: '12/12/2012 09:24', modifiedOn: 'user' },
  { reference: 'PV20230912', type: 'New', borrowerName: 'customer one', fosRef: '12092390', createdOn: '12/12/2012 09:24', modifiedOn: 'user' },
  { reference: 'PV20230912', type: 'New', borrowerName: 'customer one', fosRef: '12092390', createdOn: '12/12/2012 09:24', modifiedOn: 'user' },
  { reference: 'PV20230912', type: 'New', borrowerName: 'customer one', fosRef: '12092390', createdOn: '12/12/2012 09:24', modifiedOn: 'user' }
];

@Component({
  selector: 'app-pv-applications-view',
  templateUrl: './pv-applications-view.component.html',
  styleUrl: './pv-applications-view.component.scss'
})

export class PvApplicationsViewComponent implements OnInit {

  sourceArr: [] = [];
  appArr: [] = [];
  roleId!: number;

  constructor(private pvAppService: PVAppService, private router: Router, private applicationStorageService: ApplicationStorageService) { }


  ngOnInit(): void {
    this.roleId = this.applicationStorageService.getroleId();
    this.pvAppService.fetchApplications().subscribe((res: any) => {
      this.appArr = [...res] as any;
      this.sourceArr = [...res] as any;
    });

  }

  displayedColumns: string[] = ['reference', 'type', 'borrowerName', 'fosRef', 'createdOn', 'modifiedOn', 'actions', 'view'];
  dataSource = SAMPLE_TABLE_DATA;

  filterData($event: any) {
    let event = $event.target.value;
    if(event){
      this.appArr = this.sourceArr.filter((row:any):any[] => {
        return row.referenceNumber.includes(event) || row.fosreferenceNumber.includes(event) || row.createdOn.includes(event);
      }) as any;
    } else {
      this.appArr = [...this.sourceArr];
    }
   
  }

  createAppPage() {
    this.router.navigate(['home/pvform']);
  }


}
