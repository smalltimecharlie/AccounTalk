import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './pension-received.reducer';
import { IPensionReceived } from 'app/shared/model/pension-received.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPensionReceivedProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class PensionReceived extends React.Component<IPensionReceivedProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { pensionReceivedList, match } = this.props;
    return (
      <div>
        <h2 id="pension-received-heading">
          <Translate contentKey="accounTalkApp.pensionReceived.home.title">Pension Receiveds</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.pensionReceived.home.createLabel">Create new Pension Received</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {pensionReceivedList && pensionReceivedList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.pensionReceived.grossPensionReceived">Gross Pension Received</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.pensionReceived.taxDeducted">Tax Deducted</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.pensionReceived.taxReturn">Tax Return</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.pensionReceived.pensionProvider">Pension Provider</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {pensionReceivedList.map((pensionReceived, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${pensionReceived.id}`} color="link" size="sm">
                        {pensionReceived.id}
                      </Button>
                    </td>
                    <td>{pensionReceived.grossPensionReceived}</td>
                    <td>{pensionReceived.taxDeducted}</td>
                    <td>
                      {pensionReceived.taxReturn ? (
                        <Link to={`tax-return/${pensionReceived.taxReturn.id}`}>{pensionReceived.taxReturn.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {pensionReceived.pensionProvider ? (
                        <Link to={`pension-provider/${pensionReceived.pensionProvider.id}`}>{pensionReceived.pensionProvider.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${pensionReceived.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${pensionReceived.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${pensionReceived.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.pensionReceived.home.notFound">No Pension Receiveds found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ pensionReceived }: IRootState) => ({
  pensionReceivedList: pensionReceived.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PensionReceived);
