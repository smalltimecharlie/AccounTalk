import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './business-profit.reducer';
import { IBusinessProfit } from 'app/shared/model/business-profit.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBusinessProfitDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BusinessProfitDetail extends React.Component<IBusinessProfitDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { businessProfitEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.businessProfit.detail.title">BusinessProfit</Translate> [<b>{businessProfitEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="turnover">
                <Translate contentKey="accounTalkApp.businessProfit.turnover">Turnover</Translate>
              </span>
            </dt>
            <dd>{businessProfitEntity.turnover}</dd>
            <dt>
              <span id="expenses">
                <Translate contentKey="accounTalkApp.businessProfit.expenses">Expenses</Translate>
              </span>
            </dt>
            <dd>{businessProfitEntity.expenses}</dd>
            <dt>
              <span id="capitalAllowances">
                <Translate contentKey="accounTalkApp.businessProfit.capitalAllowances">Capital Allowances</Translate>
              </span>
            </dt>
            <dd>{businessProfitEntity.capitalAllowances}</dd>
            <dt>
              <span id="profit">
                <Translate contentKey="accounTalkApp.businessProfit.profit">Profit</Translate>
              </span>
            </dt>
            <dd>{businessProfitEntity.profit}</dd>
            <dt>
              <span id="cisTaxDeducted">
                <Translate contentKey="accounTalkApp.businessProfit.cisTaxDeducted">Cis Tax Deducted</Translate>
              </span>
            </dt>
            <dd>{businessProfitEntity.cisTaxDeducted}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.businessProfit.taxReturn">Tax Return</Translate>
            </dt>
            <dd>{businessProfitEntity.taxReturn ? businessProfitEntity.taxReturn.id : ''}</dd>
            <dt>
              <Translate contentKey="accounTalkApp.businessProfit.business">Business</Translate>
            </dt>
            <dd>{businessProfitEntity.business ? businessProfitEntity.business.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/business-profit" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/business-profit/${businessProfitEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ businessProfit }: IRootState) => ({
  businessProfitEntity: businessProfit.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BusinessProfitDetail);
