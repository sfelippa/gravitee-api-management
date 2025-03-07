/* tslint:disable */
/* eslint-disable */
/**
 * Gravitee.io - Management API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface ApiRequestItem
 */
export interface ApiRequestItem {
    /**
     * 
     * @type {string}
     * @memberof ApiRequestItem
     */
    application?: string;
    /**
     * 
     * @type {boolean}
     * @memberof ApiRequestItem
     */
    endpoint?: boolean;
    /**
     * 
     * @type {string}
     * @memberof ApiRequestItem
     */
    id?: string;
    /**
     * 
     * @type {string}
     * @memberof ApiRequestItem
     */
    method?: ApiRequestItemMethodEnum;
    /**
     * 
     * @type {string}
     * @memberof ApiRequestItem
     */
    path?: string;
    /**
     * 
     * @type {string}
     * @memberof ApiRequestItem
     */
    plan?: string;
    /**
     * 
     * @type {number}
     * @memberof ApiRequestItem
     */
    responseTime?: number;
    /**
     * 
     * @type {number}
     * @memberof ApiRequestItem
     */
    status?: number;
    /**
     * 
     * @type {number}
     * @memberof ApiRequestItem
     */
    timestamp?: number;
    /**
     * 
     * @type {string}
     * @memberof ApiRequestItem
     */
    transactionId?: string;
    /**
     * 
     * @type {string}
     * @memberof ApiRequestItem
     */
    user?: string;
}

export function ApiRequestItemFromJSON(json: any): ApiRequestItem {
    return ApiRequestItemFromJSONTyped(json, false);
}

export function ApiRequestItemFromJSONTyped(json: any, ignoreDiscriminator: boolean): ApiRequestItem {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'application': !exists(json, 'application') ? undefined : json['application'],
        'endpoint': !exists(json, 'endpoint') ? undefined : json['endpoint'],
        'id': !exists(json, 'id') ? undefined : json['id'],
        'method': !exists(json, 'method') ? undefined : json['method'],
        'path': !exists(json, 'path') ? undefined : json['path'],
        'plan': !exists(json, 'plan') ? undefined : json['plan'],
        'responseTime': !exists(json, 'responseTime') ? undefined : json['responseTime'],
        'status': !exists(json, 'status') ? undefined : json['status'],
        'timestamp': !exists(json, 'timestamp') ? undefined : json['timestamp'],
        'transactionId': !exists(json, 'transactionId') ? undefined : json['transactionId'],
        'user': !exists(json, 'user') ? undefined : json['user'],
    };
}

export function ApiRequestItemToJSON(value?: ApiRequestItem | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'application': value.application,
        'endpoint': value.endpoint,
        'id': value.id,
        'method': value.method,
        'path': value.path,
        'plan': value.plan,
        'responseTime': value.responseTime,
        'status': value.status,
        'timestamp': value.timestamp,
        'transactionId': value.transactionId,
        'user': value.user,
    };
}

/**
* @export
* @enum {string}
*/
export enum ApiRequestItemMethodEnum {
    CONNECT = 'CONNECT',
    DELETE = 'DELETE',
    GET = 'GET',
    HEAD = 'HEAD',
    OPTIONS = 'OPTIONS',
    PATCH = 'PATCH',
    POST = 'POST',
    PUT = 'PUT',
    TRACE = 'TRACE',
    OTHER = 'OTHER'
}


