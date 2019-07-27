import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './business-profit.reducer';
import { IBusinessProfit } from 'app/shared/model/business-profit.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBusinessProfitProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class BusinessProfit extends React.Component<IBusinessProfitProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { businessProfitList, match } = this.props;
    return (
      <div>
        <h2 id="business-profit-heading">
          <Translate contentKey="accounTalkApp.businessProfit.home.title">Business Profits</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.businessProfit.home.createLabel">Create new Business Profit</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {businessProfitList && businessProfitList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.businessProfit.turnover">Turnover</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.businessProfit.expenses">Expenses</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.businessProfit.capitalAllowances">Capital Allowances</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.businessProfit.profit">Profit</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.businessProfit.cisTaxDeducted">Cis Tax Deducted</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.businessProfit.taxReturn">Tax Return</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.businessProfit.business">Business</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {businessProfitList.map((businessProfit, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${businessProfit.id}`} color="link" size="sm">
                        {businessProfit.id}
                      </Button>
                    </td>
                    <td>{businessProfit.turnover}</td>
                    <td>{businessProfit.expenses}</td>
                    <td>{businessProfit.capitalAllowances}</td>
                    <td>{businessProfit.profit}</td>
                    <td>{businessProfit.cisTaxDeducted}</td>
                    <td>
                      {businessProfit.taxReturn ? (
                        <Link to={`tax-return/${businessProfit.taxReturn.id}`}>{businessProfit.taxReturn.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {businessProfit.business ? (
                        <Link to={`business/${businessProfit.business.id}`}>{businessProfit.business.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${businessProfit.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${businessProfit.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${businessProfit.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="accounTalkApp.businessProfit.home.notFound">No Business Profits found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ businessProfit }: IRootState) => ({
  businessProfitList: businessProfit.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BusinessProfit);
