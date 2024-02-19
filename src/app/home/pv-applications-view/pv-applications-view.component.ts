import { Component } from '@angular/core';

export interface PeriodicElement {
  reference: string;
  receivedOn: string;
  borrowerName: string;
  fosRef: string;
  createdOn: string;
  modifiedOn: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {reference: 'PV20230912', receivedOn: '12/12/2012 09:23', borrowerName: 'customer one', fosRef: '12092390', createdOn:'12/12/2012 09:23', modifiedOn:'12/12/2012 09:23'},
  {reference: 'PV20230912', receivedOn: '12/12/2012 09:23', borrowerName: 'customer one', fosRef: '12092390', createdOn:'12/12/2012 09:23', modifiedOn:'12/12/2012 09:23'},
  {reference: 'PV20230912', receivedOn: '12/12/2012 09:23', borrowerName: 'customer one', fosRef: '12092390', createdOn:'12/12/2012 09:23', modifiedOn:'12/12/2012 09:23'},
  {reference: 'PV20230912', receivedOn: '12/12/2012 09:23', borrowerName: 'customer one', fosRef: '12092390', createdOn:'12/12/2012 09:23', modifiedOn:'12/12/2012 09:23'},
  {reference: 'PV20230912', receivedOn: '12/12/2012 09:23', borrowerName: 'customer one', fosRef: '12092390', createdOn:'12/12/2012 09:23', modifiedOn:'12/12/2012 09:23'},
  {reference: 'PV20230912', receivedOn: '12/12/2012 09:23', borrowerName: 'customer one', fosRef: '12092390', createdOn:'12/12/2012 09:23', modifiedOn:'12/12/2012 09:23'},
  {reference: 'PV20230912', receivedOn: '12/12/2012 09:23', borrowerName: 'customer one', fosRef: '12092390', createdOn:'12/12/2012 09:23', modifiedOn:'12/12/2012 09:23'},
  {reference: 'PV20230912', receivedOn: '12/12/2012 09:23', borrowerName: 'customer one', fosRef: '12092390', createdOn:'12/12/2012 09:23', modifiedOn:'12/12/2012 09:23'}
];

@Component({
  selector: 'app-pv-applications-view',
  templateUrl: './pv-applications-view.component.html',
  styleUrl: './pv-applications-view.component.scss'
})

export class PvApplicationsViewComponent {

  displayedColumns: string[] = ['reference', 'receivedOn', 'borrowerName', 'fosRef','createdOn','modifiedOn','actions','view'];
  dataSource = ELEMENT_DATA;


}