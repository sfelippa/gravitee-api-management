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
export enum NotificationConfigType {
    PORTAL = 'PORTAL',
    GENERIC = 'GENERIC'
}

export function NotificationConfigTypeFromJSON(json: any): NotificationConfigType {
    return NotificationConfigTypeFromJSONTyped(json, false);
}

export function NotificationConfigTypeFromJSONTyped(json: any, ignoreDiscriminator: boolean): NotificationConfigType {
    return json as NotificationConfigType;
}

export function NotificationConfigTypeToJSON(value?: NotificationConfigType | null): any {
    return value as any;
}

