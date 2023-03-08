/**
 * Taxi Management API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { Trip } from './trip';
import { Driver } from './driver';
import { Taxi } from './taxi';


export interface Shift { 
    id?: number;
    date: string;
    startMileage: number;
    endMileage: number;
    /**
     * shift duration in minutes
     */
    duration?: number;
    driver: Driver;
    taxi: Taxi;
    trips?: Array<Trip>;
}
