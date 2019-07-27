import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './address.reducer';
import { IAddress } from 'app/shared/model/address.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAddressProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Address extends React.Component<IAddressProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { addressList, match } = this.props;
    return (
      <div>
        <h2 id="address-heading">
          <Translate contentKey="accounTalkApp.address.home.title">Addresses</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.address.home.createLabel">Create new Address</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {addressList && addressList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.address.address1">Address 1</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.address.address2">Address 2</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.address.address3">Address 3</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.address.town">Town</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.address.county">County</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.address.country">Country</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.address.postcode">Postcode</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.address.client">Client</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.address.previousAccountant">Previous Accountant</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {addressList.map((address, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${address.id}`} color="link" size="sm">
                        {address.id}
                      </Button>
                    </td>
                    <td>{address.address1}</td>
                    <td>{address.address2}</td>
                    <td>{address.address3}</td>
                    <td>{address.town}</td>
                    <td>{address.county}</td>
                    <td>{address.country}</td>
                    <td>{address.postcode}</td>
                    <td>{address.client ? <Link to={`client/${address.client.id}`}>{address.client.id}</Link> : ''}</td>
                    <td>
                      {address.previousAccountant ? (
                        <Link to={`previous-accountant/${address.previousAccountant.id}`}>{address.previousAccountant.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${address.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${address.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${address.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="accounTalkApp.address.home.notFound">No Addresses found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ address }: IRootState) => ({
  addressList: address.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Address);
