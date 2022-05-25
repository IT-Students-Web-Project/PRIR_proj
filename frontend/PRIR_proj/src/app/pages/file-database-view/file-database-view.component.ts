import { Component, OnInit } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {FileService} from "../../files/file.service";
import {TextFile} from "../../models/text-file";

@Component({
  selector: 'app-file-database-view',
  templateUrl: './file-database-view.component.html',
  styleUrls: ['./file-database-view.component.css']
})
export class FileDatabaseViewComponent implements OnInit {
  userName = JSON.parse(localStorage.getItem('user'))['username'];
  textFiles: TextFile[]

  constructor(private router: Router, private toastr: ToastrService, private fileService: FileService) { }

  ngOnInit(): void {
    this.fileService.getUserFiles(this.userName).subscribe((textFiles: TextFile[]) => {this.textFiles = textFiles})
  }
  verifyClicked(id: number) {
    this.router.navigateByUrl('/file-verification/' + id);
  }
}
