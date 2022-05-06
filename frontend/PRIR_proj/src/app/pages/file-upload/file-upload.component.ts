import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { FileService } from 'src/app/files/file.service';
import { FileInfo } from 'src/app/models/file-request';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  userName = JSON.parse(localStorage.getItem('user'))['username'];
  file: File;
  fileId: BehaviorSubject<string> = new BehaviorSubject<string>(null);
  form: NgForm;

  constructor(private router: Router, private toastr: ToastrService, private fileService: FileService) { }

  ngOnInit(): void {
    this.fileId.subscribe(() => this.updateFileInfo());
  }

  onFileSelected(event) {
    this.file = event.target.files[0];
  }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      this.showError('Spróbuj ponownie', 'Błędne dane')
      return;
    }

    this.form = form;

    const formData = new FormData();
    formData.append("document", this.file);

    this.fileService.uploadFile(formData).subscribe((fileId) => {
      console.log(fileId);
      this.fileId.next(fileId);
    });
  }

  updateFileInfo() {
    let fileInfo = this.buildFileInfo();
    this.fileService.updateFileInfo(fileInfo).subscribe();
    this.form.reset();
    this.router.navigateByUrl('/file-verification/' + fileInfo.id);
  }

  buildFileInfo(): FileInfo {
    return {
      id: this.fileId.value,
      fileName: this.form.value.fileName,
      user: this.userName
    }
  }

  showSuccess(message: string, title: string) {
    this.toastr.success(message, title);
  }

  showError(message: string, title: string) {
    this.toastr.error(message, title);
  }

}
