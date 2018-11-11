import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ITransportLine } from '../model/ITransportLine';

@Injectable({
  providedIn: 'root'
})
export class TransportLineService {

  constructor(private http: HttpClient) { }

  getTransportLines(): Observable<ITransportLine[]>{
    var qwe: Observable<ITransportLine[]>;
    qwe = this.http.get<ITransportLine[]>('/server/transportLine/all');
    return qwe;
  }
}


