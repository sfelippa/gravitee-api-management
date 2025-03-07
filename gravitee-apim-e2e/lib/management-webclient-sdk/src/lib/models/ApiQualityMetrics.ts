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
 * @interface ApiQualityMetrics
 */
export interface ApiQualityMetrics {
    /**
     * 
     * @type {number}
     * @memberof ApiQualityMetrics
     */
    categoriesWeight?: number;
    /**
     * 
     * @type {number}
     * @memberof ApiQualityMetrics
     */
    descriptionMinLength?: number;
    /**
     * 
     * @type {number}
     * @memberof ApiQualityMetrics
     */
    descriptionWeight?: number;
    /**
     * 
     * @type {boolean}
     * @memberof ApiQualityMetrics
     */
    enabled?: boolean;
    /**
     * 
     * @type {number}
     * @memberof ApiQualityMetrics
     */
    functionalDocumentationWeight?: number;
    /**
     * 
     * @type {number}
     * @memberof ApiQualityMetrics
     */
    healthcheckWeight?: number;
    /**
     * 
     * @type {number}
     * @memberof ApiQualityMetrics
     */
    labelsWeight?: number;
    /**
     * 
     * @type {number}
     * @memberof ApiQualityMetrics
     */
    logoWeight?: number;
    /**
     * 
     * @type {number}
     * @memberof ApiQualityMetrics
     */
    technicalDocumentationWeight?: number;
}

export function ApiQualityMetricsFromJSON(json: any): ApiQualityMetrics {
    return ApiQualityMetricsFromJSONTyped(json, false);
}

export function ApiQualityMetricsFromJSONTyped(json: any, ignoreDiscriminator: boolean): ApiQualityMetrics {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'categoriesWeight': !exists(json, 'categoriesWeight') ? undefined : json['categoriesWeight'],
        'descriptionMinLength': !exists(json, 'descriptionMinLength') ? undefined : json['descriptionMinLength'],
        'descriptionWeight': !exists(json, 'descriptionWeight') ? undefined : json['descriptionWeight'],
        'enabled': !exists(json, 'enabled') ? undefined : json['enabled'],
        'functionalDocumentationWeight': !exists(json, 'functionalDocumentationWeight') ? undefined : json['functionalDocumentationWeight'],
        'healthcheckWeight': !exists(json, 'healthcheckWeight') ? undefined : json['healthcheckWeight'],
        'labelsWeight': !exists(json, 'labelsWeight') ? undefined : json['labelsWeight'],
        'logoWeight': !exists(json, 'logoWeight') ? undefined : json['logoWeight'],
        'technicalDocumentationWeight': !exists(json, 'technicalDocumentationWeight') ? undefined : json['technicalDocumentationWeight'],
    };
}

export function ApiQualityMetricsToJSON(value?: ApiQualityMetrics | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'categoriesWeight': value.categoriesWeight,
        'descriptionMinLength': value.descriptionMinLength,
        'descriptionWeight': value.descriptionWeight,
        'enabled': value.enabled,
        'functionalDocumentationWeight': value.functionalDocumentationWeight,
        'healthcheckWeight': value.healthcheckWeight,
        'labelsWeight': value.labelsWeight,
        'logoWeight': value.logoWeight,
        'technicalDocumentationWeight': value.technicalDocumentationWeight,
    };
}


