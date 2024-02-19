import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "./home.component";
import { PvformComponent } from "./pvform/pvform.component";
import { PvApplicationsViewComponent } from "./pv-applications-view/pv-applications-view.component";


const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        children: [
            { path: 'pvform', component: PvformComponent },
            { path: 'pvview', component: PvApplicationsViewComponent },
          ]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule { }