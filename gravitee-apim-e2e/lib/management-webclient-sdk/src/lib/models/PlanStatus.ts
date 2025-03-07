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

/**
 * 
 * @export
 * @enum {string}
 */
export enum PlanStatus {
    STAGING = 'STAGING',
    PUBLISHED = 'PUBLISHED',
    CLOSED = 'CLOSED',
    DEPRECATED = 'DEPRECATED'
}

export function PlanStatusFromJSON(json: any): PlanStatus {
    return PlanStatusFromJSONTyped(json, false);
}

export function PlanStatusFromJSONTyped(json: any, ignoreDiscriminator: boolean): PlanStatus {
    return json as PlanStatus;
}

export function PlanStatusToJSON(value?: PlanStatus | null): any {
    return value as any;
}

