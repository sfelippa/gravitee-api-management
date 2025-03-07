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
 * @interface UpdateTenantEntity
 */
export interface UpdateTenantEntity {
    /**
     * 
     * @type {string}
     * @memberof UpdateTenantEntity
     */
    description?: string;
    /**
     * 
     * @type {string}
     * @memberof UpdateTenantEntity
     */
    id?: string;
    /**
     * 
     * @type {string}
     * @memberof UpdateTenantEntity
     */
    name: string;
}

export function UpdateTenantEntityFromJSON(json: any): UpdateTenantEntity {
    return UpdateTenantEntityFromJSONTyped(json, false);
}

export function UpdateTenantEntityFromJSONTyped(json: any, ignoreDiscriminator: boolean): UpdateTenantEntity {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'description': !exists(json, 'description') ? undefined : json['description'],
        'id': !exists(json, 'id') ? undefined : json['id'],
        'name': json['name'],
    };
}

export function UpdateTenantEntityToJSON(value?: UpdateTenantEntity | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'description': value.description,
        'id': value.id,
        'name': value.name,
    };
}


